package com.alex.springmusicproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageServiceImpl implements StorageService {
    private final String DIR =  "music";
    private static final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);
    @Autowired
    private ResourceLoader loader;

    public void store(MultipartFile file) {
        Path path = Paths.get(DIR + "/" + file.getOriginalFilename());

        try {
            Files.write(path, file.getBytes());
            logger.info("Track was uploaded: " + file.getOriginalFilename());
        } catch (IOException e) {
            logger.warn("Error writing file: " + e.getMessage());
        }
    }


    @PostConstruct
    public void init() {
        Path path = Paths.get(DIR);

        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
                logger.info("The music dir is created: " + path);

            } catch (IOException e) {
                logger.error("Error creating music folder: " + e.getMessage());
                System.exit(1);
            }
        }
    }

    public void deleteAll() {
        try {
            Files.deleteIfExists(Paths.get(DIR));
        } catch (IOException e) {
            logger.error("Error deleting file: " + e.getMessage());
        }
    }

    @Override
    public Resource loadResource() {
        UrlResource urlResource = null;
        try {
            urlResource = new UrlResource(new File("music/m.mp3").toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return urlResource;
    }
}
