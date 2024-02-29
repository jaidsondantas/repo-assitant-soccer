package com.assistant.main.usecase;

import com.assistant.main.entities.Game;
import com.assistant.main.entities.GameApi;
import com.assistant.main.gateway.ExternalAPIUseCase;
import com.assistant.main.gateway.GameDataFromExternalApiUseCase;
import com.assistant.main.gateway.SaveGameUseCase;
import com.assistant.main.helpers.APIHeaders;
import com.assistant.main.helpers.APIParams;
import com.assistant.main.mappers.GameMapper;
import com.assistant.main.services.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GameDataFromExternalApiUseCaseImpl implements GameDataFromExternalApiUseCase {
    private final ExternalAPIUseCase externalAPIUseCase;
    private final ObjectMapper objectMapper;
    private final GameService gameService;
    private final SaveGameUseCase saveGameUseCase;
    private final GameMapper gameMapper;

    public GameDataFromExternalApiUseCaseImpl(ExternalAPIUseCase externalAPIUseCase, ObjectMapper objectMapper, GameService gameService, SaveGameUseCase saveGameUseCase, GameMapper gameMapper) {
        this.externalAPIUseCase = externalAPIUseCase;
        this.objectMapper = objectMapper;
        this.gameService = gameService;
        this.saveGameUseCase = saveGameUseCase;
        this.gameMapper = gameMapper;
    }

    @Override
    public Game getGame(Long gameId) throws JsonProcessingException {
        String url = "https://webws.365scores.com/web/game/";

        APIParams apiParams = new APIParams();
        APIHeaders apiHeaders = new APIHeaders();

        Map<String, String> params = apiParams.buildParams();
        params.put("gameId", gameId.toString());
        HttpHeaders headers = apiHeaders.buildHeaders();

        String result = externalAPIUseCase.getDataFromExternalAPI(url, params, headers);
        GameApi gameApi = objectMapper.readValue(result, GameApi.class);

        Game game = gameService.parseGameFromResult(gameApi.getGame());

        saveGameUseCase.saveGame(gameMapper.entityToDto(game));

        return game;
    }

}
