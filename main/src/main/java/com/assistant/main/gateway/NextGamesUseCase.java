package com.assistant.main.gateway;

import com.assistant.main.entities.NextGame;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface NextGamesUseCase {
    List<NextGame> getNextGames() throws JsonProcessingException;
}
