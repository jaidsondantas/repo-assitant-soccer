package com.assistant.main.controllers;

import com.assistant.main.entities.NextGame;
import com.assistant.main.gateway.NextGamesUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NextGameController {

    private final NextGamesUseCase nextGamesUseCase;

    @Autowired
    public NextGameController(NextGamesUseCase nextGamesUseCase) {
        this.nextGamesUseCase = nextGamesUseCase;
    }

    @GetMapping("/next-games")
    public ResponseEntity<List<NextGame>> getNextGames(
    ) throws JsonProcessingException {
        List<NextGame> nextGames = nextGamesUseCase.getNextGames();

        return ResponseEntity.ok(nextGames);
    }
}
