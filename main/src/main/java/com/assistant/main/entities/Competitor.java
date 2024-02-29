package com.assistant.main.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Competitor {

    private Long id;
    private int countryId;
    private int sportId;
    private String name;
    private String longName;
    private double score;
    private Statistic statistic;
    private List<Integer> recentMatches;
    private List<Statistic> statistics;
    private Boolean isWinner;
}
