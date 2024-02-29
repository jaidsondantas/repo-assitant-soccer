package com.assistant.main.entities;

import lombok.Data;

import java.util.List;
import java.util.Map;


@Data
public class GameApi {
    Map<String, Object> game;

    public Map<String, Object> getGame() {
        return game;
    }
}
