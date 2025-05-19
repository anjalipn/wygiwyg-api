package com.example.wygiwyg.controller;

import com.example.wygiwyg.model.WygiwygRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WygiwygController.class)
class WygiwygControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnSuccessResponse() throws Exception {
        WygiwygRequest request = new WygiwygRequest();
        request.setStatusCode(200);
        request.setBody(Map.of("message", "Hello World"));

        mockMvc.perform(post("/api/wygiwyg/response")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello World"));
    }

    @Test
    void shouldReturnCustomStatusCode() throws Exception {
        WygiwygRequest request = new WygiwygRequest();
        request.setStatusCode(201);
        request.setBody(Map.of("id", 1, "name", "Test"));

        mockMvc.perform(post("/api/wygiwyg/response")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test"));
    }

    @Test
    void shouldReturnCustomHeaders() throws Exception {
        WygiwygRequest request = new WygiwygRequest();
        request.setStatusCode(200);
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Custom-Header", "CustomValue");
        request.setHeaders(headers);
        request.setBody(Map.of("message", "With custom headers"));

        mockMvc.perform(post("/api/wygiwyg/response")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Custom-Header", "CustomValue"))
                .andExpect(jsonPath("$.message").value("With custom headers"));
    }

    @Test
    void shouldReturnEmptyBody() throws Exception {
        WygiwygRequest request = new WygiwygRequest();
        request.setStatusCode(204);

        mockMvc.perform(post("/api/wygiwyg/response")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    void shouldReturnErrorResponse() throws Exception {
        WygiwygRequest request = new WygiwygRequest();
        request.setStatusCode(404);
        request.setBody(Map.of(
            "error", "Resource not found",
            "code", "NOT_FOUND"
        ));

        mockMvc.perform(post("/api/wygiwyg/response")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource not found"))
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    void shouldReturnComplexResponse() throws Exception {
        WygiwygRequest request = new WygiwygRequest();
        request.setStatusCode(200);
        Map<String, Object> user = new HashMap<>();
        user.put("id", 1);
        user.put("name", "John Doe");
        user.put("email", "john@example.com");
        user.put("roles", new String[]{"USER", "ADMIN"});

        Map<String, Object> body = new HashMap<>();
        body.put("user", user);
        body.put("metadata", Map.of(
            "createdAt", "2024-03-18T12:00:00Z",
            "version", "1.0.0"
        ));
        request.setBody(body);

        mockMvc.perform(post("/api/wygiwyg/response")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.name").value("John Doe"))
                .andExpect(jsonPath("$.user.email").value("john@example.com"))
                .andExpect(jsonPath("$.user.roles[0]").value("USER"))
                .andExpect(jsonPath("$.user.roles[1]").value("ADMIN"))
                .andExpect(jsonPath("$.metadata.createdAt").value("2024-03-18T12:00:00Z"))
                .andExpect(jsonPath("$.metadata.version").value("1.0.0"));
    }
} 