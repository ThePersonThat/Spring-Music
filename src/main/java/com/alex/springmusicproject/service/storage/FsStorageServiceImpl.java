package com.alex.springmusicproject.service.storage;

import com.alex.springmusicproject.excpetion.UserFolderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
        try {
            Path path = Paths.get(makePath(removeExtension(file.getOriginalFilename()), username));
            File folder = new File(path.toUri());

            if (!folder.mkdir()) {
                logger.warn("Error creating music folder: " + folder.getName());
                throw new UserFolderNotFoundException("The folder of the music could not create");
            }

            Files.write(Paths.get(folder.getPath() + "/music.mp3"), file.getBytes());
            logger.info("Track was uploaded: " + file.getOriginalFilename());
        } catch (IOException e) {
            logger.warn("Error writing file: " + e.getMessage());
        }
    }

    @Override
    public void store(BufferedImage image, String musicName, String username) {
        File file = new File(makePath(removeExtension(musicName), username) + "/image.png");

        try {
            ImageIO.write(image, "png", file);
            logger.info("Image was loaded for " + username);
        } catch (IOException e) {
            logger.error("Error saving image: " + e.getMessage() + "user: " + username);
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
    public Resource loadMusic(String username, String filename)  {
        return loadResource(username, removeExtension(filename) + "/music.mp3");
    }

    @Override
    public byte[] loadImage(String username, String musicName) {
        Resource resource = loadResource(username, removeExtension(musicName) + "/image.png");
        byte[] array = null;

        try {
            array = FileCopyUtils.copyToByteArray(resource.getFile());
        } catch (IOException e) {
            logger.error("Error load image for " + username);
        }

        return array;
    }

    private Resource loadResource(String username, String filename) {
        String path = DIR + "/" + username;

        if (!Files.exists(Paths.get(path))) {
            String msg = username + " does not have own folder!";
            logger.warn(msg);
            throw new UserFolderNotFoundException(msg);
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

    private String removeExtension(String filename) {
        return filename.substring(0, filename.indexOf('.'));
    }
}
