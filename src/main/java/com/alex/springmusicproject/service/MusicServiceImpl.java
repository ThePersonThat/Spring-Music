package com.alex.springmusicproject.service;

import com.alex.springmusicproject.entity.Music;
import com.alex.springmusicproject.entity.User;
import com.alex.springmusicproject.excpetion.MusicNotFoundException;
import com.alex.springmusicproject.repo.MusicRepo;
import com.alex.springmusicproject.service.storage.DatabaseStorageService;
import com.alex.springmusicproject.service.storage.FsStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class MusicServiceImpl implements MusicService {
    private final UserService userService;
    private final DatabaseStorageService databaseStorage;
    private final FsStorageService fsStorage;
    private final MusicRepo musicRepo;

    @Autowired
    public MusicServiceImpl(UserService userService, DatabaseStorageService databaseStorage, FsStorageService fsStorage, MusicRepo musicRepo) {
        this.userService = userService;
        this.databaseStorage = databaseStorage;
        this.fsStorage = fsStorage;
        this.musicRepo = musicRepo;
    }

    @Override
    public void saveMusic(MultipartFile file, String username) {
        User user = userService.getUserByUsername(username);
        fsStorage.store(file, username);
        databaseStorage.save(file, user);
    }

    @Override
    public List<Music> getMusicList(String username) {
        User user = userService.getUserByUsername(username);
        return user.getMusicList();
    }

    @Override
    public Resource loadMusic(String username, String id) {
        Optional<Music> music = musicRepo.findById(Long.parseLong(id));

        if (music.isPresent()) {
            return fsStorage.loadResource(username, music.get().getFilename());
        } else {
            throw new MusicNotFoundException("Music: " + id + " with username: " + username + " was not found");
        }
    }
}
