package com.serjer.task.consumer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.serjer.task.consumer.model.ClientRequest;
import com.serjer.task.consumer.model.ContentResponse;
import com.serjer.task.consumer.service.ProcessingService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ClientController {

    final private ProcessingService processingService;

    public ClientController(ProcessingService processingService) {
        this.processingService = processingService;
    }

    @PostMapping("/import-content")
    @ResponseBody
    public ResponseEntity<HttpStatus> importClientItem(@RequestBody ClientRequest request){
        log.info("POST/import-content getting request={}", request);
        processingService.importContent(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/submitted-content")
    public List<ContentResponse> getSubmittedContext(){
        log.info("GET/submitted-content getting request");
        return processingService.getSubmittedContent();
    }

}
