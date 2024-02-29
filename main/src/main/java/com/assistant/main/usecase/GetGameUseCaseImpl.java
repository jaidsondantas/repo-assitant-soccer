package com.assistant.main.usecase;

import com.assistant.main.entities.Game;
import com.assistant.main.gateway.GetGameUseCase;
import com.assistant.main.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetGameUseCaseImpl implements GetGameUseCase {

    private final GameRepository gameRepository;

    public GetGameUseCaseImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }
}
