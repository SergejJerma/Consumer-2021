package com.visma.task.consumer.service;

import com.visma.task.consumer.model.Status;
import com.visma.task.consumer.model.StatusType;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class ProcessingService {

    private static final String URL_INIT = "http://localhost:8037/thirdpartyservice/init";
    private static final String URL_GET = "http://localhost:8037/thirdpartyservice/checkStatus/{uuid}";

    private final RestfulService restfulService;

    public ProcessingService(RestfulService restfulService) {
        this.restfulService = restfulService;
    }

    @PostConstruct
    public void init() {
        String uuid = callInit();
        Status status = getStatus(uuid);
        log.info("Status received: {}", status);
    }

    public String callInit() {
        String content = "content";
        ResponseEntity<String> response = restfulService.postJson(URL_INIT, content, String.class);
        return response.getBody();
    }

    public Status getStatus(String uuid) {
        ResponseEntity<Status> response = restfulService.get(URL_GET, Status.class, uuid);
        return response.getBody();
    }
}
