package com.assistant.main.mappers;

import com.assistant.main.dtos.GameDTO;
import com.assistant.main.entities.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface GameMapper {

    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    @Mapping(target = "id", ignore = true)
    Game dtoToEntity(GameDTO gameDTO);

    GameDTO entityToDto(Game game);
}

