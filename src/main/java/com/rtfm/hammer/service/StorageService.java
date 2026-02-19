package com.rtfm.hammer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {
    private final Path rootLocation;

    public StorageService(@Value("${app.storage.location}") String location) {
        this.rootLocation = Paths.get(location);
        initialize();
    }

    private void initialize() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Storage konnte nicht erstellt werden", e);
        }
    }

    public void save(String filename, String content) throws IOException {
        Files.write(rootLocation.resolve(filename), content.getBytes());
    }
 }
