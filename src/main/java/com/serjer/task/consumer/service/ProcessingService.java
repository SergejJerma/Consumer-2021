package com.serjer.task.consumer.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.serjer.task.consumer.model.ClientRequest;
import com.serjer.task.consumer.model.ContentResponse;
import com.serjer.task.consumer.model.Item;
import com.serjer.task.consumer.model.Status;
import com.serjer.task.consumer.model.StatusType;
import com.serjer.task.consumer.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;

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
        ResponseEntity<String> response = restfulService.postJson(URL_INIT, request.getContent(), String.class);
        createAndSaveItem(response.getBody(), request.getContent());
    }

    @Transactional
    protected void createAndSaveItem(String uuid, String content) {
        Item item = new Item(uuid, content, getStatus(uuid).getStatusType().name(), LocalDateTime.now(), null);
        itemRepository.save(item);
    }

    @Transactional
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

    private Status getStatus(String uuid) {
        ResponseEntity<Status> response = restfulService.get(URL_GET, Status.class, uuid);
        return response.getBody();
    }

    public List<ContentResponse> getSubmittedContent() {
        return itemRepository.findAllItemsByStatusType(StatusType.OK.name())
                .stream()
                .map(item -> new ContentResponse(item.getContent()))
                .collect(Collectors.toList());
    }
}
