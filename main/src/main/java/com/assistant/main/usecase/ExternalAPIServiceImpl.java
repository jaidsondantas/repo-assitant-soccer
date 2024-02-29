package com.assistant.main.usecase;

import com.assistant.main.gateway.ExternalAPIUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ExternalAPIServiceImpl implements ExternalAPIUseCase {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getDataFromExternalAPI(String url, Map<String, String> params, HttpHeaders headers) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                buildUrlWithParams(url, params),
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch data from external API");
        }
    }

    private String buildUrlWithParams(String url, Map<String, String> params) {
        StringBuilder builder = new StringBuilder(url);
        builder.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return builder.toString();
    }

}
