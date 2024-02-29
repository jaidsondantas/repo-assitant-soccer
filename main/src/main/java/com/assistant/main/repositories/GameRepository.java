package com.assistant.main.repositories;

import com.assistant.main.entities.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<Game, Long> {
}
