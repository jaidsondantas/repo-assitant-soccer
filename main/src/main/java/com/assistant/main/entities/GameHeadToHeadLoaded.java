package com.assistant.main.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Optional;

@Document(collection = "game_head_to_head_loaded")
@Data
public class GameHeadToHeadLoaded {
    private Long gameId;

    private List<Game> headToHeads;
}
