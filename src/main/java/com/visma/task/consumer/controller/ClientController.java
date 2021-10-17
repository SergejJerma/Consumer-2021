package com.visma.task.consumer.controller;

import com.visma.task.consumer.model.ClientRequest;
import com.visma.task.consumer.model.ContentResponse;
import com.visma.task.consumer.service.ProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ClientController {

    final private ProcessingService processingService;

    public ClientController(ProcessingService processingService) {
        this.processingService = processingService;
    }

    @PostMapping("/import-content")
    @ResponseStatus(HttpStatus.OK)
    public void importClientItem(@RequestBody ClientRequest request){
        log.info("POST/import-item getting request={}", request);
        processingService.importContent(request);
    }

    @GetMapping("/submitted-content")
    public List<ContentResponse> getSubmittedContext(){
        log.info("GET/submitted-content getting request");
        return processingService.getSubmittedContent();
    }

}
