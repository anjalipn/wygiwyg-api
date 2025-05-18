package com.example.wygiwyg.model;

import lombok.Data;
import java.util.Map;

@Data
public class WygiwygRequest {
    private int statusCode = 200;
    private Map<String, String> headers;
    private Object body;
} 