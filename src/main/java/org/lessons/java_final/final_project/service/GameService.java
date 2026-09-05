package org.lessons.java_final.final_project.service;

import java.util.List;
import java.util.Optional;

import org.lessons.java_final.final_project.model.Developer;
import org.lessons.java_final.final_project.model.Game;
import org.lessons.java_final.final_project.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private DeveloperService developerService;

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Optional<Game> findById(Integer id) {
        Optional<Game> singleGame = gameRepository.findById(id);
        return singleGame;
    }

    public Game getById(Integer id) {

        if (findById(id).isEmpty()) {
            throw new RuntimeException("Game non trovato con id: " + id);
        }

        return findById(id).get();
    }

    public Game create(Game game) {

        Developer developer = developerService
                .findByNameIgnoreCase(game.getDeveloperName())
                .orElseGet(() -> {
                    Developer d = new Developer();
                    d.setName(game.getDeveloperName());
                    return developerService.create(d);
                });

        game.setDeveloper(developer);

        return gameRepository.save(game);
    }

    public Game update(Game game) {
        Developer developer = developerService
                .findByNameIgnoreCase(game.getDeveloperName())
                .orElseGet(() -> {
                    Developer d = new Developer();
                    d.setName(game.getDeveloperName());
                    return developerService.create(d);
                });

        game.setDeveloper(developer);

        return gameRepository.save(game);
    }

    public void delete(Game game) {
        gameRepository.delete(game);
    }

}
