package org.lessons.java_final.final_project.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.lessons.java_final.final_project.model.Developer;
import org.lessons.java_final.final_project.model.Game;
import org.lessons.java_final.final_project.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private GameService gameService;

    public List<Developer> findAll() {
        return developerRepository.findAll();
    }

    public Developer getById(Integer id) {
        Optional<Developer> singleDeveloper = developerRepository.findById(id);

        if (singleDeveloper.isEmpty()) {
            throw new NoSuchElementException("Sviluppatore non trovato con id: " + id);
        }

        return singleDeveloper.get();
    }

    public Developer create(Developer developer) {
        return developerRepository.save(developer);
    }

    public Developer update(Developer developer) {
        return developerRepository.save(developer);
    }

    public void delete(Integer id) {

        Developer developerToDelete = getById(id);

        for (Game gameToDelete : developerToDelete.getGames()) {
            gameService.delete(gameToDelete.getId());
        }

        developerRepository.delete(developerToDelete);
    }
}