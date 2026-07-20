package org.lessons.java_final.final_project.repository;

import org.lessons.java_final.final_project.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepository extends JpaRepository<Developer, Integer> {

}
