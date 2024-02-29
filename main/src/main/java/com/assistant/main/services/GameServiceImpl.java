package com.assistant.main.services;

import com.assistant.main.entities.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    @Override
    public Game parseGameFromResult(Map<String, Object> gameMap) {
        Game game = new Game();
        game.setId(Optional.ofNullable((Integer) gameMap.get("id")).map(Integer::longValue).orElse(null));
        game.setCompetitionId((Integer) gameMap.get("competitionId"));
        game.setCompetitionDisplayName((String) gameMap.get("competitionDisplayName"));
        game.setStartTime((String) gameMap.get("startTime"));
        game.setStatusText((String) gameMap.get("statusText"));


        // Preenchendo homeCompetitor
        Map<String, Object> homeCompetitorMap = (Map<String, Object>) gameMap.get("homeCompetitor");
        Competitor homeCompetitor = new Competitor();
        homeCompetitor.setId(Optional.ofNullable((Integer) homeCompetitorMap.get("id")).map(Integer::longValue).orElse(null));
        homeCompetitor.setCountryId((int) homeCompetitorMap.get("countryId"));
        homeCompetitor.setSportId((int) homeCompetitorMap.get("sportId"));
        homeCompetitor.setName((String) homeCompetitorMap.get("name"));
        homeCompetitor.setLongName((String) homeCompetitorMap.get("longName"));
        homeCompetitor.setScore((double) homeCompetitorMap.get("score"));
        homeCompetitor.setRecentMatches((List<Integer>) homeCompetitorMap.get("recentMatches"));

        List<Statistic> statistics = homeCompetitorMap.get("statistics") != null ?
                (List<Statistic>) homeCompetitorMap.get("statistics") : new ArrayList<>();

        homeCompetitor.setStatistics(statistics);
        homeCompetitor.setIsWinner((Boolean) homeCompetitorMap.get("isWinner"));
        game.setHomeCompetitor(homeCompetitor);


        // Preenchendo awayCompetitor
        Map<String, Object> awayCompetitorMap = (Map<String, Object>) gameMap.get("awayCompetitor");
        Competitor awayCompetitor = new Competitor();
        awayCompetitor.setId(Optional.ofNullable((Integer) awayCompetitorMap.get("id")).map(Integer::longValue).orElse(null));
        awayCompetitor.setCountryId((int) awayCompetitorMap.get("countryId"));
        awayCompetitor.setSportId((int) awayCompetitorMap.get("sportId"));
        awayCompetitor.setName((String) awayCompetitorMap.get("name"));
        awayCompetitor.setScore((double) awayCompetitorMap.get("score"));
        awayCompetitor.setRecentMatches((List<Integer>) awayCompetitorMap.get("recentMatches"));

        List<Statistic> awayStatistics = awayCompetitorMap.get("statistics") != null ?
                (List<Statistic>) awayCompetitorMap.get("statistics") : new ArrayList<>();

        awayCompetitor.setStatistics(awayStatistics);
        awayCompetitor.setIsWinner((Boolean) awayCompetitorMap.get("isWinner"));
        game.setAwayCompetitor(awayCompetitor);

        // Preenchendo venue
        Map<String, Object> venueMap = (Map<String, Object>) gameMap.get("venue");
        if(venueMap != null) {
            Venue venue = new Venue();
            venue.setId(Optional.ofNullable((Integer) venueMap.get("id")).map(Integer::longValue).orElse(null));
            venue.setName((String) venueMap.get("name"));
            venue.setShortName((String) venueMap.get("shortName"));
            game.setVenue(venue);
        }



        return game;
    }


}
