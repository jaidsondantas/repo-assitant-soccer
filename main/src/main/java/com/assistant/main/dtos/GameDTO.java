package com.assistant.main.dtos;

import com.assistant.main.entities.Competitor;
import com.assistant.main.entities.Venue;
import lombok.Data;

@Data
public class GameDTO {
    private long id;
    private String competitionDisplayName;
    private String startTime;
    private String statusText;
    private Competitor homeCompetitor;
    private Competitor awayCompetitor;
    private Venue venue;
}

