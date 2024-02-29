package com.assistant.main.gateway;

import com.assistant.main.entities.Game;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GetHeadToHeadExternalApiUseCase {
    public List<Game> getHeadToHead(Long gameId) throws JsonProcessingException;
}
