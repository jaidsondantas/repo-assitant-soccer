package com.assistant.main.usecase;

import com.assistant.main.entities.*;
import com.assistant.main.gateway.GameDataFromExternalApiUseCase;
import com.assistant.main.gateway.GetGameUseCase;
import com.assistant.main.gateway.GetHeadToHeadExternalApiUseCase;
import com.assistant.main.gateway.GetStatisticsUseCase;
import com.assistant.main.repositories.GameHeadToHeadLoadedRepository;
import com.assistant.main.services.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GetStatisticsUseCaseImpl implements GetStatisticsUseCase {

    private final GetGameUseCase getGameUseCase;
    private final GameDataFromExternalApiUseCase gameDataFromExternalApiUseCase;
    private final GetHeadToHeadExternalApiUseCase getHeadToHeadExternalApiUseCase;
    private final GameHeadToHeadLoadedRepository gameHeadToHeadLoadedRepository;

    public GetStatisticsUseCaseImpl(GetGameUseCaseImpl getGameUseCase, GameDataFromExternalApiUseCase gameDataFromExternalApiUseCase, GameService gameService, GetHeadToHeadExternalApiUseCase getHeadToHeadExternalApiUseCase, GameHeadToHeadLoadedRepository gameHeadToHeadLoadedRepository) {
        this.getGameUseCase = getGameUseCase;
        this.gameDataFromExternalApiUseCase = gameDataFromExternalApiUseCase;
        this.getHeadToHeadExternalApiUseCase = getHeadToHeadExternalApiUseCase;
        this.gameHeadToHeadLoadedRepository = gameHeadToHeadLoadedRepository;
    }

    public FinalStatistic getStatistics(Long gameId) throws JsonProcessingException {
        Game futureGame = getGame(gameId);
        List<Game> headToHead = getHeadToHead(gameId);


        FinalStatistic finalStatistic = buildStatistics(futureGame, headToHead);

        return finalStatistic;

    }

    private Game getGame(Long gameId) throws JsonProcessingException {
        Game game = null;

        Optional<Game> gameOptional = getGameUseCase.getGameById(gameId);

        if (gameOptional.isPresent()) {
            game = gameOptional.get();
        } else {
            Game newGame = gameDataFromExternalApiUseCase.getGame(gameId);
            game = newGame;
            try {
                Thread.sleep(2000); // 3000 milissegundos = 3 segundos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return game;
    }

    private List<Game> getHeadToHead(Long gameId) throws JsonProcessingException {
        List<Game> gameHeadToHeadLoaded = new ArrayList<>();

        Optional<GameHeadToHeadLoaded> gameHeadToHeadLoadedOptional = gameHeadToHeadLoadedRepository.findByGameId(gameId);

        if (gameHeadToHeadLoadedOptional.isPresent()) {
            gameHeadToHeadLoaded = gameHeadToHeadLoadedOptional.get().getHeadToHeads();
        } else {
            gameHeadToHeadLoaded = getHeadToHeadExternalApiUseCase.getHeadToHead(gameId);
        }
        return gameHeadToHeadLoaded;

    }

    public FinalStatistic buildStatistics(Game game, List<Game> headToHead) throws JsonProcessingException {

        FinalStatistic finalStatistic = new FinalStatistic();

        FinalStatistic.Team homeTeam = createTeamFromCompetitor(game, game.getHomeCompetitor(), headToHead);
        FinalStatistic.Team awayTeam = createTeamFromCompetitor(game, game.getAwayCompetitor(), headToHead);




        finalStatistic.setTeamHome(homeTeam);
        finalStatistic.setTeamAway(awayTeam);

        return finalStatistic;
    }


    private FinalStatistic.Team createTeamFromCompetitor(Game game, Competitor competitor, List<Game> headToHead) throws JsonProcessingException {
        try {
            FinalStatistic.Team team = new FinalStatistic.Team();
            team.setName(competitor.getName());

            List<Game> matches = populateMatchesWithGames(competitor.getRecentMatches());
            Map<String, Object> dataExtracted = extractedPerformance(competitor, game, matches);

            team.setRecentPerformance(createRecentPerformance(dataExtracted));
            team.setGoalsScored((int) dataExtracted.get("goalsScored"));
            team.setGoalsConceded((int) dataExtracted.get("goalsConceded"));
            team.setPossession(createRecentValues(dataExtracted, "possessionMatches"));
            team.setShotsOnGoal(createShotsGoal(dataExtracted, "shotsOnGoal"));
            team.setHomePerformance(extractedPerformanceInHome(competitor, game, matches));
            team.setAwayPerformance(extractedPerformanceInAway(competitor, game, matches));

            List<FinalStatistic.HeadToHead> listHomeHead = createHeadToHead(competitor, headToHead);
            FinalStatistic.HeadToHead head = new FinalStatistic.HeadToHead();
            head.setWins(listHomeHead.get(0).getWins() + listHomeHead.get(1).getWins());
            head.setLosses(listHomeHead.get(0).getLosses() + listHomeHead.get(1).getLosses());
            head.setDraws(listHomeHead.get(0).getDraws() + listHomeHead.get(1).getDraws());
            team.setHeadToHead(head);

            return team;
        } finally {

        }
    }

    private FinalStatistic.RecentPerformance createRecentPerformance(Map<String, Object> data) {
        FinalStatistic.RecentPerformance recentPerformance = new FinalStatistic.RecentPerformance();
        recentPerformance.setLastMatches((List) data.get("recentPerformanceLastMatches"));
        return recentPerformance;
    }

    private FinalStatistic.RecentValues createRecentValues(Map<String, Object> data, String key) {
        FinalStatistic.RecentValues recentValues = new FinalStatistic.RecentValues();
        recentValues.setLastMatches((List) data.get(key));
        return recentValues;
    }

    private FinalStatistic.ShotsGoal createShotsGoal(Map<String, Object> data, String key) {
        FinalStatistic.ShotsGoal recentValues = new FinalStatistic.ShotsGoal();
        recentValues.setLastMatches((List) data.get(key));
        return recentValues;
    }

    private List<FinalStatistic.HeadToHead> createHeadToHead(Competitor competitor, List<Game> headToheads) {
        List<FinalStatistic.HeadToHead> data = new ArrayList<>();
        int winsHome = 0;
        int drawsHome = 0;
        int lossesHome = 0;
        int winsAway = 0;
        int drawsAway = 0;
        int lossesAway = 0;

        for (int i = 0; i < headToheads.size(); i++) {
            Game currentlyGame = headToheads.get(i);
            if(competitor.getId().equals(currentlyGame.getHomeCompetitor().getId())) {
                if(currentlyGame.getHomeCompetitor().getScore() > currentlyGame.getAwayCompetitor().getScore()) {
                    winsHome+=1;
                }
                else if(currentlyGame.getHomeCompetitor().getScore() < currentlyGame.getAwayCompetitor().getScore()) {
                    lossesHome+=1;
                } else {
                    drawsHome+=1;
                }
            }

            if(competitor.getId().equals(currentlyGame.getAwayCompetitor().getId())) {
                if(currentlyGame.getAwayCompetitor().getScore() > currentlyGame.getHomeCompetitor().getScore()) {
                    winsAway+=1;
                }
                else if(currentlyGame.getAwayCompetitor().getScore() < currentlyGame.getHomeCompetitor().getScore()) {
                    lossesAway+=1;
                } else {
                    drawsAway+=1;
                }
            }

        }

        FinalStatistic.HeadToHead resultHome = new FinalStatistic.HeadToHead();
        resultHome.setWins(winsHome);
        resultHome.setLosses(lossesHome);
        resultHome.setDraws(drawsHome);

        data.add(resultHome);

        FinalStatistic.HeadToHead resultAway = new FinalStatistic.HeadToHead();
        resultAway.setWins(winsAway);
        resultAway.setLosses(lossesAway);
        resultAway.setDraws(drawsAway);

        data.add(resultAway);


        return data;
    }

    private static Map<String, Object> extractedPerformance(Competitor competitor, Game game, List<Game> matchesHome) {
        Map<String, Object> data = new HashMap<>();
        List<String> recentPerformanceLastMatches = new ArrayList<>();
        List<Double> possessionMatches = new ArrayList<>();
        List<String> shotsOnGoal = new ArrayList<>();


        int goalsScored = 0;
        int goalsConceded = 0;

        for (int i = 0; i < matchesHome.size(); i++) {
            Game currentlyGame = matchesHome.get(i);
            boolean isHome = currentlyGame.getHomeCompetitor().getId().equals(competitor.getId());
            Competitor myTeam = isHome ? currentlyGame.getHomeCompetitor() : currentlyGame.getAwayCompetitor();
            Competitor otherTeam = !isHome ? currentlyGame.getHomeCompetitor() : currentlyGame.getAwayCompetitor();
            String result = myTeam.getScore() > otherTeam.getScore() ? "winner" : myTeam.getScore() < otherTeam.getScore() ? "losses" : "draw";

            recentPerformanceLastMatches.add(result);

            goalsScored += myTeam.getScore();
            goalsConceded += otherTeam.getScore();
            Statistic foundStatisticPossession = null;
            Statistic foundStatisticShotsOnGoal = null;
            if (myTeam.getStatistics() != null && myTeam.getStatistics() instanceof List) {
                for (Statistic stat : myTeam.getStatistics()) {
                    if ("Posse de bola".equals(stat.getName())) {
                        foundStatisticPossession = stat;
                        break;
                    }
                }
                for (Statistic stat : myTeam.getStatistics()) {
                    if ("Chutes no gol".equals(stat.getName())) {
                        foundStatisticShotsOnGoal = stat;
                        break;
                    }
                }
            }
            if (foundStatisticPossession != null) {
                possessionMatches.add(foundStatisticPossession.getValuePercentage());
            }
            if (foundStatisticShotsOnGoal != null) {
                shotsOnGoal.add(foundStatisticShotsOnGoal.getValue());
            }
        }
        data.put("recentPerformanceLastMatches", recentPerformanceLastMatches);
        data.put("goalsScored", goalsScored);
        data.put("goalsConceded", goalsConceded);
        data.put("possessionMatches", possessionMatches);
        data.put("shotsOnGoal", shotsOnGoal);

        return data;
    }

    private static FinalStatistic.Performance extractedPerformanceInHome(Competitor competitor, Game game, List<Game> matchesHome) {
        FinalStatistic.Performance data = new FinalStatistic.Performance();
        int wins = 0;
        int draws = 0;
        int losses = 0;

        for (int i = 0; i < matchesHome.size(); i++) {
            Game currentlyGame = matchesHome.get(i);
            boolean isHome = Objects.equals(currentlyGame.getHomeCompetitor().getId(), competitor.getId());
            if (isHome) {
                String result = currentlyGame.getHomeCompetitor().getScore() > currentlyGame.getAwayCompetitor().getScore() ? "winner" : currentlyGame.getHomeCompetitor().getScore() < currentlyGame.getAwayCompetitor().getScore() ? "losses" : "draw";
                switch (result) {
                    case "winner": {
                        wins += 1;
                        break;
                    }
                    case "losses": {
                        losses += 1;
                        break;
                    }
                    case "draw": {
                        draws += 1;
                        break;
                    }
                }
            }
        }
        data.setWins(wins);
        data.setLosses(losses);
        data.setDraws(draws);

        return data;
    }

    private static FinalStatistic.Performance extractedPerformanceInAway(Competitor competitor, Game game, List<Game> matches) {
        FinalStatistic.Performance data = new FinalStatistic.Performance();
        int wins = 0;
        int draws = 0;
        int losses = 0;

        for (int i = 0; i < matches.size(); i++) {
            Game currentlyGame = matches.get(i);
            boolean isAway = currentlyGame.getAwayCompetitor().getId().equals(competitor.getId());
            if (isAway) {
                String result = currentlyGame.getAwayCompetitor().getScore() > currentlyGame.getHomeCompetitor().getScore() ? "winner" : currentlyGame.getAwayCompetitor().getScore() < currentlyGame.getHomeCompetitor().getScore() ? "losses" : "draw";
                switch (result) {
                    case "winner": {
                        wins += 1;
                        break;
                    }
                    case "losses": {
                        losses += 1;
                        break;
                    }
                    case "draw": {
                        draws += 1;
                        break;
                    }
                }
            }
        }
        data.setWins(wins);
        data.setLosses(losses);
        data.setDraws(draws);

        return data;
    }

    private List<Game> populateMatchesWithGames(List<Integer> matches) throws JsonProcessingException {
        List<Game> games = new ArrayList<>();

        for (int i = 0; i < Math.min(10, matches.size()); i++) {
            Integer id = matches.get(i);
            Long matchId = Long.valueOf(id);
            Game matchGame = getGame(matchId);
            if (matchGame != null) {
                games.add(matchGame);
            }


        }

        return games;
    }
}
