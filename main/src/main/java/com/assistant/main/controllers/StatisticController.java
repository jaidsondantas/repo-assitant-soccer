package com.assistant.main.controllers;

import com.assistant.main.entities.FinalStatistic;
import com.assistant.main.gateway.GetStatisticsUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {

    private final GetStatisticsUseCase getStatisticsUseCase;

    @Autowired
    public StatisticController(GetStatisticsUseCase getStatisticsUseCase) {
        this.getStatisticsUseCase = getStatisticsUseCase;
    }

    @GetMapping("/get-statistic-json")
    public FinalStatistic getStatistic(
            @RequestParam("gameId") Long gameId
    ) throws JsonProcessingException {
        return getStatisticsUseCase.getStatistics(gameId);
    }
}
