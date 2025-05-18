package com.example.dummyapi.model;

import lombok.Data;
import java.util.Map;

@Data
public class DummyRequest {
    private int statusCode = 200;
    private Map<String, String> headers;
    private Object body;
} 