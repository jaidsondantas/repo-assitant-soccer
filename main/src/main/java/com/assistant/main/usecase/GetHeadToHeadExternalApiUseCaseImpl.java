package com.assistant.main.usecase;

import com.assistant.main.entities.Game;
import com.assistant.main.entities.GameApi;
import com.assistant.main.entities.GameHeadToHeadLoaded;
import com.assistant.main.gateway.*;
import com.assistant.main.helpers.APIHeaders;
import com.assistant.main.helpers.APIParams;
import com.assistant.main.mappers.GameMapper;
import com.assistant.main.services.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GetHeadToHeadExternalApiUseCaseImpl implements GetHeadToHeadExternalApiUseCase {

    private final ExternalAPIUseCase externalAPIUseCase;
    private final ObjectMapper objectMapper;
    private final GameService gameService;
    private final SaveGameUseCase saveGameUseCase;
    private final GetGameUseCase getGameUseCase;
    private final GameMapper gameMapper;
    private final SaveHeadToHeadUseCase gameHeadToHeadLoadedRepository;

    public GetHeadToHeadExternalApiUseCaseImpl(ExternalAPIUseCase externalAPIUseCase, ObjectMapper objectMapper, GameService gameService, SaveGameUseCase saveGameUseCase, GetGameUseCase getGameUseCase, GameMapper gameMapper, SaveHeadToHeadUseCase gameHeadToHeadLoadedRepository1) {
        this.externalAPIUseCase = externalAPIUseCase;
        this.objectMapper = objectMapper;
        this.gameService = gameService;
        this.saveGameUseCase = saveGameUseCase;
        this.getGameUseCase = getGameUseCase;
        this.gameMapper = gameMapper;
        this.gameHeadToHeadLoadedRepository = gameHeadToHeadLoadedRepository1;
    }

    @Override
    public List<Game> getHeadToHead(Long gameId) throws JsonProcessingException {
        String url = "https://webws.365scores.com/web/games/h2h/";

        APIParams apiParams = new APIParams();
        APIHeaders apiHeaders = new APIHeaders();

        Map<String, String> params = apiParams.buildParams();
        params.put("gameId", gameId.toString());
        HttpHeaders headers = apiHeaders.buildHeaders();

        String result = externalAPIUseCase.getDataFromExternalAPI(url, params, headers);
        GameApi gameApi = objectMapper.readValue(result, GameApi.class);

        Map<String, Object> listGamesHeadToHead = gameApi.getGame();
        List<Game> gamesList = new ArrayList<>();
        List<Object> list = (List<Object>) listGamesHeadToHead.get("h2hGames");


        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> gameToHead = (Map<String, Object>) list.get(i);
            Game game = gameService.parseGameFromResult(gameToHead);
//            saveGameUseCase.saveGame(gameMapper.entityToDto(game));
            gamesList.add(game);
        }

        GameHeadToHeadLoaded gameHeadToHeadLoaded = new GameHeadToHeadLoaded();
        gameHeadToHeadLoaded.setGameId(gameId);
        gameHeadToHeadLoaded.setHeadToHeads(gamesList);

        gameHeadToHeadLoadedRepository.saveHeadToHeads(gameHeadToHeadLoaded);


        return gamesList;
    }
}
