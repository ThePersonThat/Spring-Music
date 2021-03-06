package com.alex.springmusicproject.service;

import com.alex.springmusicproject.entity.Music;
import com.alex.springmusicproject.entity.User;
import com.alex.springmusicproject.repo.MusicRepo;
import com.alex.springmusicproject.service.storage.DataBaseStorageService;
import com.alex.springmusicproject.service.storage.FsStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

@Service
public class MusicService {
    private final UserService userService;
    private final DataBaseStorageService databaseStorage;
    private final FsStorageService fsStorage;
    private final MusicRepo musicRepo;
    private final ImageService imageService;
    private final Logger logger = LoggerFactory.getLogger(MusicService.class);


    public MusicService(UserService userService, DataBaseStorageService databaseStorage,
                        FsStorageService fsStorage, MusicRepo musicRepo, ImageService imageService) {
        this.userService = userService;
        this.databaseStorage = databaseStorage;
        this.fsStorage = fsStorage;
        this.musicRepo = musicRepo;
        this.imageService = imageService;
    }

    public void saveMusic(MultipartFile file, String username) {
        User user = userService.getUserByUsername(username);
        Music music = parseFile(file);

        fsStorage.store(file, username);
        databaseStorage.save(music, user);
        BufferedImage image = imageService.getImage(music.getBand());
        fsStorage.store(image, file.getOriginalFilename(), username);
    }

    public List<Music> getMusicList(String username) {
        User user = userService.getUserByUsername(username);

        return user.getMusicList();
    }


    public Resource loadMusic(String username, String id) {
        Optional<Music> music = musicRepo.findById(Long.parseLong(id));

        if (music.isPresent()) {
            return fsStorage.loadMusic(username, music.get().getFilename());
        } else {
            String msg = "Music: " + id + " with username: " + username + " was not found";
            logger.warn(msg);
            throw new IllegalStateException(msg);
        }
    }

    public byte[] loadImage(String username, String id) {
        Optional<Music> music = musicRepo.findById(Long.parseLong(id));

        if (music.isPresent()) {
            return fsStorage.loadImage(username, music.get().getFilename());
        } else {
            String msg = "Music: " + id + " with username: " + username + " was not found";
            logger.warn(msg);
            throw new IllegalStateException(msg);
        }
    }

    private Music parseFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String[] tokens = filename.split("-");
        String band = tokens[0];
        String songName = tokens[1].substring(0, tokens[1].indexOf('.'));

        return new Music(band, songName, filename);
    }
}
