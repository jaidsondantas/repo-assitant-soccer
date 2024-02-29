package com.assistant.main.usecase;

import com.assistant.main.entities.GameHeadToHeadLoaded;
import com.assistant.main.gateway.SaveHeadToHeadUseCase;
import com.assistant.main.repositories.GameHeadToHeadLoadedRepository;
import org.springframework.stereotype.Service;

@Service
public class SaveHeadToHeadUseCaseImpl implements SaveHeadToHeadUseCase {

    private final GameHeadToHeadLoadedRepository gameHeadToHeadLoadedRepository;

    public SaveHeadToHeadUseCaseImpl(GameHeadToHeadLoadedRepository gameRepository) {
        this.gameHeadToHeadLoadedRepository = gameRepository;
    }

    public GameHeadToHeadLoaded saveHeadToHeads(GameHeadToHeadLoaded gameHeadToHeadLoaded) {
        return gameHeadToHeadLoadedRepository.save(gameHeadToHeadLoaded);
    }
}
