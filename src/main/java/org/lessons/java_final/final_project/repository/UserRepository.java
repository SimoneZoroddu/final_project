package org.lessons.java_final.final_project.repository;

import java.util.Optional;

import org.lessons.java_final.final_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
