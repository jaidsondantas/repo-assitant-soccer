package com.assistant.main.gateway;

import com.assistant.main.entities.Game;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface GameDataFromExternalApiUseCase {
    public Game getGame(Long gameId) throws JsonProcessingException;
}
