package com.assistant.main.services;

import com.assistant.main.entities.Game;
import com.assistant.main.entities.HeadToHead;

import java.util.Map;

public interface GameService {
    public Game parseGameFromResult(Map<String, Object> gameMap);
}
