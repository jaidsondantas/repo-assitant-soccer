package com.assistant.main.gateway;

import org.springframework.http.HttpHeaders;

import java.util.Map;

public interface ExternalAPIUseCase {
    String getDataFromExternalAPI(String url, Map<String, String> params, HttpHeaders headers);
}
