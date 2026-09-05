package org.lessons.java_final.final_project.api;

import java.util.List;
import java.util.Optional;

import org.lessons.java_final.final_project.model.Game;
import org.lessons.java_final.final_project.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("games/api")
public class GameRestController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public List<Game> index() {

        List<Game> games = gameService.findAll();

        return games;
    }

    @GetMapping("{id}")
    public ResponseEntity<Game> show(@PathVariable Integer id) {
        Optional<Game> attemptGame = gameService.findById(id);

        if (attemptGame.isEmpty()) {
            return new ResponseEntity<Game>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(attemptGame.get(), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Game> store(@Valid @RequestBody Game gameToSave) {

        return new ResponseEntity<Game>(gameService.create(gameToSave), HttpStatus.OK);

    }

    @PutMapping("{id}")
    public ResponseEntity<Game> update(@Valid @RequestBody Game gameToEdit, @PathVariable Integer id) {
        Optional<Game> attemptGame = gameService.findById(id);
        if (attemptGame.isEmpty()) {
            return new ResponseEntity<Game>(HttpStatus.NOT_FOUND);
        }

        gameToEdit.setId(id);

        return new ResponseEntity<Game>(gameService.update(gameToEdit), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<Game> attemptGame = gameService.findById(id);

        if (attemptGame.isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        gameService.delete(attemptGame.get());

        return new ResponseEntity<Void>(HttpStatus.OK);

    }
}
