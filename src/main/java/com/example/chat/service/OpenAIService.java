package com.example.chat.service;

import com.example.chat.model.ChatRequestDto;
import com.example.chat.model.ChatResponseDto;
import com.example.chat.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class OpenAIService {
    @Value("${API_KEY}")
    private String apiKey;
    @Value("${API_URL}")
    private String url;
    @Value("${API_MODEL}")
    private String model;

    private RestTemplate template = new RestTemplate();
    public Message chat(String prompt){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        ChatRequestDto request = new ChatRequestDto(model, prompt);
        HttpEntity<ChatRequestDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity< ChatResponseDto> response = template.postForEntity(url, entity, ChatResponseDto.class);
        return response.getBody().getChoices().get(0).getMessage();
    }
}
