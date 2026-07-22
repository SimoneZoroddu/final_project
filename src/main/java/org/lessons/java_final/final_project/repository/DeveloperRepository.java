package org.lessons.java_final.final_project.repository;

import java.util.Optional;

import org.lessons.java_final.final_project.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
    Optional<Developer> findByNameIgnoreCase(String name);

}
