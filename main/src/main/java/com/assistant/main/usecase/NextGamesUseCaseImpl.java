package com.assistant.main.usecase;

import com.assistant.main.entities.Game;
import com.assistant.main.entities.NextGame;
import com.assistant.main.entities.NextGameApi;
import com.assistant.main.gateway.ExternalAPIUseCase;
import com.assistant.main.gateway.NextGamesUseCase;
import com.assistant.main.gateway.SaveNextGameUseCase;
import com.assistant.main.helpers.APIHeaders;
import com.assistant.main.helpers.APIParams;
import com.assistant.main.services.NextGameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NextGamesUseCaseImpl implements NextGamesUseCase {

    private final ExternalAPIUseCase externalAPIUseCase;
    private final SaveNextGameUseCase saveNextGameUseCase;
    private final NextGameService nextGameService;

    private final ObjectMapper objectMapper;

    @Autowired
    public NextGamesUseCaseImpl(ExternalAPIUseCase externalAPIUseCase, SaveNextGameUseCase saveNextGameUseCase, NextGameService nextGameService, ObjectMapper objectMapper) {
        this.externalAPIUseCase = externalAPIUseCase;
        this.saveNextGameUseCase = saveNextGameUseCase;
        this.nextGameService = nextGameService;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<NextGame> getNextGames() throws JsonProcessingException {


        String url = "https://webws.365scores.com/web/games/featured/";

        APIParams apiParams = new APIParams();
        APIHeaders apiHeaders = new APIHeaders();

        Map<String, String> params = apiParams.buildParams();
        params.put("sports", "1");
        params.put("showOdds", "true");
        params.put("numberOfGames", "4");
        params.put("context", "1");
        params.put("topBookmaker", "139");

        HttpHeaders headers = apiHeaders.buildHeaders();
        String result = externalAPIUseCase.getDataFromExternalAPI(url, params, headers);

        NextGameApi nextGameApi = objectMapper.readValue(result, NextGameApi.class);

        List<NextGame> games = nextGameService.parseNextGamesFromResult(nextGameApi.getGames());
        saveNextGameUseCase.saveGames(games);

        return games;
    }


}

