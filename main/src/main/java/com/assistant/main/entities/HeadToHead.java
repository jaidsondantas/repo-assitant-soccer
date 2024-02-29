package com.assistant.main.entities;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class HeadToHead {

    Long id;

    Map<String, HtoHead> game;

    public Map<String, HtoHead> getGame() {
        return game;
    }

    @Data
    public static class HtoHead {
        List< Map<String, Object>> h2hGames;
    }

}
