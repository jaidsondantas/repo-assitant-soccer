package com.assistant.main.gateway;

import com.assistant.main.entities.NextGame;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SaveNextGameUseCase {
    public void saveGames(List<NextGame> games);
}
