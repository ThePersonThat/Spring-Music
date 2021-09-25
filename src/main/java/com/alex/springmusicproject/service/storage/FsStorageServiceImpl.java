package com.alex.springmusicproject.service.storage;

import com.alex.springmusicproject.entity.Music;
import com.alex.springmusicproject.excpetion.UserFolderNotFoundException;
import com.alex.springmusicproject.service.MusicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
public class FsStorageServiceImpl implements FsStorageService {
    private final String DIR =  "music";
    private static final Logger logger = LoggerFactory.getLogger(FsStorageServiceImpl.class);


    @Override
    public void store(MultipartFile file, String username) {
        Path path = Paths.get(makePath(file.getOriginalFilename(), username));

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
    public Resource loadResource(String username, String filename)  {
        String path = DIR + "/" + username;

        if (!Files.exists(Paths.get(path))) {
            throw new UserFolderNotFoundException(username + " does not have own folder!");
        }

        UrlResource urlResource = null;

        try {
            urlResource = new UrlResource(new File(path + "/" + filename).toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return urlResource;
    }

    private String makePath(String filename, String username) {
        return DIR + "/" + username + "/" + filename;
    }
}
