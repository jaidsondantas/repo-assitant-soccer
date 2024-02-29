package com.assistant.main.repositories;

import com.assistant.main.entities.NextGame;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MongoNextGameRepository implements NextGameRepository {

    private final MongoTemplate mongoTemplate;

    public MongoNextGameRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void save(NextGame game) {
        mongoTemplate.save(game);
    }
}
