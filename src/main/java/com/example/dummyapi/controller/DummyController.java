package com.example.dummyapi.controller;

import com.example.dummyapi.model.DummyRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dummy")
public class DummyController {

    @PostMapping("/response")
    public ResponseEntity<Object> getCustomResponse(@RequestBody DummyRequest request) {
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(request.getStatusCode());
        
        if (request.getHeaders() != null) {
            request.getHeaders().forEach(responseBuilder::header);
        }
        
        // If body is null, return empty response
        if (request.getBody() == null) {
            return responseBuilder.build();
        }
        
        return responseBuilder.body(request.getBody());
    }
} 