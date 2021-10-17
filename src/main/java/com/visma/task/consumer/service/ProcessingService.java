package com.visma.task.consumer.service;

import com.visma.task.consumer.model.*;
import com.visma.task.consumer.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProcessingService {

    private static final String URL_INIT = "http://localhost:8037/thirdpartyservice/init";
    private static final String URL_GET = "http://localhost:8037/thirdpartyservice/checkStatus/{uuid}";

    private final RestfulService restfulService;
    private final ItemRepository itemRepository;

    public ProcessingService(RestfulService restfulService, ItemRepository itemRepository) {
        this.restfulService = restfulService;
        this.itemRepository = itemRepository;
    }

    @Async
    public void importContent(ClientRequest request) {
        try {
            ResponseEntity<String> response = restfulService.postJson(URL_INIT, request.getContent(), String.class);
            if (Objects.nonNull(response)) {
                String uuid = response.getBody();
                createAndSaveItem(uuid, request.getContent());
            } else {
                log.info("Null response for content: {}", request.getContent());
            }
        } catch (Exception ex) {
            log.info("Error:{} during importing content: {}", ex.getMessage(), request.getContent());
        }
    }

    private void createAndSaveItem(String uuid, String content) {
        Item item = new Item();
        item.setUuidTps(uuid);
        item.setContent(content);
        item.setStatusType(getStatus(uuid).getStatusType().name());
        item.setDateCreated(LocalDateTime.now());
        itemRepository.save(item);
    }

    public void updateItemStatus(List<Item> itemsInProgress) {
        var updatedItems = itemsInProgress
                .stream()
                .map(i -> {
                    Status status = getStatus(i.getUuidTps());
                    Item item = itemRepository.findItemByUuidTps(status.getUuid());
                    item.setStatusType(status.getStatusType().name());
                    item.setDateModified(LocalDateTime.now());
                    return itemRepository.save(item);
                }).collect(Collectors.toList());
        log.info("Updated items={}", updatedItems);
    }

    public Status getStatus(String uuid) {
        try {
            ResponseEntity<Status> response = restfulService.get(URL_GET, Status.class, uuid);
            if (Objects.nonNull(response)) {
                return response.getBody();
            } else {
                return new Status(uuid, StatusType.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.info("Error: {} during checking for status UUID: {}", ex.getMessage(), uuid);
            return new Status(uuid, StatusType.NOT_FOUND);
        }
    }

    public List<ContentResponse> getSubmittedContent() {
        return itemRepository.findAllItemsByStatusType(StatusType.OK.name())
                .stream()
                .map(item -> new ContentResponse(item.getContent()))
                .collect(Collectors.toList());
    }
}
