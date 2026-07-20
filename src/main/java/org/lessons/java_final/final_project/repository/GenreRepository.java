package org.lessons.java_final.final_project.repository;

import org.lessons.java_final.final_project.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

}