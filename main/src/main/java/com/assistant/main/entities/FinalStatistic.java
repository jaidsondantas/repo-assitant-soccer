package com.assistant.main.entities;

import lombok.Data;

import java.util.List;

@Data
public class FinalStatistic {

    private Team teamHome;
    private Team teamAway;

    @Data
    public static class Team {
        private String name;
        private HeadToHead headToHead;
        private RecentPerformance recentPerformance;
        private int goalsScored;
        private int goalsConceded;
        private Performance homePerformance;
        private Performance awayPerformance;
        private RecentValues possession;
        private ShotsGoal shotsOnGoal;
    }

    @Data
    public static class HeadToHead {
        private int wins;
        private int draws;
        private int losses;
    }

    @Data
    public static class RecentPerformance {
        private List<String> lastMatches;
        private int points;
    }

    @Data
    public static class Performance {
        private int wins;
        private int draws;
        private int losses;
    }

    @Data
    public static class RecentValues {
        private List<Double> lastMatches;
    }

    @Data
    public static class ShotsGoal {
        private List<String> lastMatches;
    }
}
