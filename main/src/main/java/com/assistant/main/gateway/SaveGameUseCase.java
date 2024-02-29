package com.assistant.main.gateway;

import com.assistant.main.dtos.GameDTO;
import com.assistant.main.entities.Game;

public interface SaveGameUseCase {
    Game saveGame(GameDTO gameDTO);
}

