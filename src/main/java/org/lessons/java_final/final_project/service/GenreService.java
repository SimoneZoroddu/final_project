package org.lessons.java_final.final_project.service;

import java.util.List;
import java.util.Optional;

import org.lessons.java_final.final_project.model.Genre;
import org.lessons.java_final.final_project.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Genre getById(Integer id) {
        Optional<Genre> singleGenre = genreRepository.findById(id);

        if (singleGenre.isEmpty()) {
            throw new RuntimeException("Genere non trovato con id: " + id);
        }

        return singleGenre.get();
    }

    public Genre create(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre update(Genre genre) {
        return genreRepository.save(genre);
    }

    public void delete(Genre genre) {
        genreRepository.delete(genre);
    }
}
