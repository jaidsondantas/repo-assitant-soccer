package com.assistant.main.mappers;

import com.assistant.main.dtos.GameDTO;
import com.assistant.main.entities.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Mapper
@Service
public class GameMapperImpl implements GameMapper {

    public static final GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    @Mapping(target = "id", ignore = true)
    public Game dtoToEntity(GameDTO gameDTO) {
        if (gameDTO == null) {
            return null;
        }
        Game game = new Game();
        game.setId(gameDTO.getId());
        game.setCompetitionId(gameDTO.getId());
        game.setCompetitionDisplayName(gameDTO.getCompetitionDisplayName());
        game.setStartTime(gameDTO.getStartTime());
        game.setStatusText(gameDTO.getStatusText());
        game.setHomeCompetitor(gameDTO.getHomeCompetitor());
        game.setAwayCompetitor(gameDTO.getAwayCompetitor());
        game.setVenue(gameDTO.getVenue());
        return game;
    }

    public GameDTO entityToDto(Game game) {
        if (game == null) {
            return null;
        }
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(game.getId());
        gameDTO.setCompetitionDisplayName(game.getCompetitionDisplayName());
        gameDTO.setStartTime(game.getStartTime());
        gameDTO.setStatusText(game.getStatusText());
        gameDTO.setHomeCompetitor(game.getHomeCompetitor());
        gameDTO.setAwayCompetitor(game.getAwayCompetitor());
        gameDTO.setVenue(game.getVenue());
        return gameDTO;
    }
}
