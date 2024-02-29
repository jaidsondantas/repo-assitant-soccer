package com.assistant.main.gateway;

import com.assistant.main.dtos.GameDTO;
import com.assistant.main.entities.Game;
import com.assistant.main.entities.GameHeadToHeadLoaded;
import com.assistant.main.entities.NextGame;

import java.util.List;

public interface SaveHeadToHeadUseCase {
    GameHeadToHeadLoaded saveHeadToHeads(GameHeadToHeadLoaded games);
}

