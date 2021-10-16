package com.visma.task.consumer.cron;

import com.visma.task.consumer.model.Item;
import com.visma.task.consumer.model.StatusType;
import com.visma.task.consumer.repository.ItemRepository;
import com.visma.task.consumer.service.ProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StatusCheck {

    private final ProcessingService processingService;
    private final ItemRepository itemRepository;

    public StatusCheck(ProcessingService processingService, ItemRepository itemRepository) {
        this.processingService = processingService;
        this.itemRepository = itemRepository;
    }

    @Scheduled(fixedRate = 20000)
    public void checkItemStatus() {
        List<Item> itemsInProgress = itemRepository.findAllItemsByStatusType(StatusType.IN_PROGRESS.name());
        log.info("Number of items in progress: {}", itemsInProgress.size());
        if (!itemsInProgress.isEmpty()) {
            updateItemStatus(itemsInProgress);
        }
    }

    private void updateItemStatus(List<Item> itemsInProgress) {
        var updatedItems =itemsInProgress
                .stream()
                .map(item -> processingService.getStatus(item.getUuidTps()))
                .map(status -> {
                    Item item = itemRepository.findItemByUuidTps(status.getUuid());
                    item.setStatusType(status.getStatusType().name());
                    item.setDateModified(LocalDateTime.now());
                    return itemRepository.save(item);
                }).collect(Collectors.toList());
        log.info("Updated items={}", updatedItems);
    }
}
