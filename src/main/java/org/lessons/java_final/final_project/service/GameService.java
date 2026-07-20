package org.lessons.java_final.final_project.service;

import java.util.List;
import java.util.Optional;

import org.lessons.java_final.final_project.model.Game;
import org.lessons.java_final.final_project.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Game getById(Integer id) {
        Optional<Game> singleGame = gameRepository.findById(id);

        if (singleGame.isEmpty()) {
            throw new RuntimeException("Game non trovato con id: " + id);
        }

        return singleGame.get();
    }

    public Game create(Game game) {
        return gameRepository.save(game);
    }

    public Game update(Game game) {
        return gameRepository.save(game);
    }

    public void delete(Game game) {
        gameRepository.delete(game);
    }

}
