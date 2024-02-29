package com.assistant.main.gateway;

import com.assistant.main.entities.FinalStatistic;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface GetStatisticsUseCase {
    FinalStatistic getStatistics(Long id) throws JsonProcessingException;
}

