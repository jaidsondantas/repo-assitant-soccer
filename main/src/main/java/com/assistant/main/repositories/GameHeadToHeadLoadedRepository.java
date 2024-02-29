package com.assistant.main.repositories;

import com.assistant.main.entities.Game;
import com.assistant.main.entities.GameHeadToHeadLoaded;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameHeadToHeadLoadedRepository extends MongoRepository<GameHeadToHeadLoaded, Long> {
    @Query("{ 'gameId' : ?0 }")
    Optional<GameHeadToHeadLoaded> findByGameId(Long gameId);
}
