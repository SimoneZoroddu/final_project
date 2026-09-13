package org.lessons.java_final.final_project.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.lessons.java_final.final_project.model.Platform;
import org.lessons.java_final.final_project.repository.PlatformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlatformService {

    @Autowired
    private PlatformRepository platformRepository;

    public List<Platform> findAll() {
        return platformRepository.findAll();
    }

    public Platform getById(Integer id) {
        Optional<Platform> singlePlatform = platformRepository.findById(id);

        if (singlePlatform.isEmpty()) {
            throw new NoSuchElementException("Piattaforma non trovato con id: " + id);
        }

        return singlePlatform.get();
    }

    public Platform create(Platform platform) {
        return platformRepository.save(platform);
    }

    public Platform update(Platform platform) {
        return platformRepository.save(platform);
    }

    public void delete(Platform platform) {
        platformRepository.delete(platform);
    }
}
