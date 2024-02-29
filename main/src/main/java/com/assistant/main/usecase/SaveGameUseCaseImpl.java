package com.assistant.main.usecase;

import com.assistant.main.dtos.GameDTO;
import com.assistant.main.entities.Game;
import com.assistant.main.gateway.SaveGameUseCase;
import com.assistant.main.mappers.GameMapper;
import com.assistant.main.repositories.GameRepository;
import org.springframework.stereotype.Service;

@Service
public class SaveGameUseCaseImpl implements SaveGameUseCase {

    private GameRepository gameRepository;
    private GameMapper gameMapper;

    public SaveGameUseCaseImpl(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    public Game saveGame(GameDTO gameDTO) {
        Game game = gameMapper.dtoToEntity(gameDTO);
        return gameRepository.save(game);
    }
}
