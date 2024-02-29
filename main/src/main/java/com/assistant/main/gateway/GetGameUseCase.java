package com.assistant.main.gateway;

import com.assistant.main.entities.Game;

import java.util.List;
import java.util.Optional;

public interface GetGameUseCase {
    List<Game> getAllGames();

    Optional<Game> getGameById(Long id);
}

