package com.assistant.main.usecase;

import com.assistant.main.entities.NextGame;
import com.assistant.main.gateway.SaveNextGameUseCase;
import com.assistant.main.repositories.NextGameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveNextNextGameUseCaseImpl implements SaveNextGameUseCase {

    private final NextGameRepository nextGameRepository;

    public SaveNextNextGameUseCaseImpl(NextGameRepository nextGameRepository) {
        this.nextGameRepository = nextGameRepository;
    }

    public void saveGames(List<NextGame> games) {
        for (NextGame game : games) {
            nextGameRepository.save(game);
        }
    }
}

