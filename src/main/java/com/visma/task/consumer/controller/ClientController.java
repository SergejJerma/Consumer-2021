package com.visma.task.consumer.controller;

import com.visma.task.consumer.model.ClientRequest;
import com.visma.task.consumer.service.ProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ClientController {

    final private ProcessingService processingService;

    public ClientController(ProcessingService processingService) {
        this.processingService = processingService;
    }

    @PostMapping("/import-item")
    public void importClientItem(@RequestBody ClientRequest request){
        log.info("POST/import-item getting request={}", request);
        processingService.importContent(request);
    }

    @GetMapping("/submitted-content")
    public List<String> getSubmittedContext(){
        log.info("GET/submitted-content getting request");
        return processingService.getSubmittedContent();
    }

}
