package com.assistant.main.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "games")
public class Game {
    private long id;
    private long competitionId;
    private long stageNum;
    private long roundNum;
    private String competitionDisplayName;
    private String startTime;
    private String statusText;
    private Competitor homeCompetitor;
    private Competitor awayCompetitor;
    private Venue venue;

}
