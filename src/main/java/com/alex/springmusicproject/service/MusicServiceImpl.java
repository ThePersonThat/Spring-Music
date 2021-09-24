package com.alex.springmusicproject.service;

import com.alex.springmusicproject.entity.Music;
import com.alex.springmusicproject.entity.User;
import com.alex.springmusicproject.service.storage.DatabaseStorageService;
import com.alex.springmusicproject.service.storage.FsStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MusicServiceImpl implements MusicService {
    private final UserService userService;
    private final DatabaseStorageService databaseStorage;
    private final FsStorageService fsStorage;

    @Autowired
    public MusicServiceImpl(UserService userService, DatabaseStorageService databaseStorage, FsStorageService fsStorage) {
        this.userService = userService;
        this.databaseStorage = databaseStorage;
        this.fsStorage = fsStorage;
    }


    @Override
    public void saveMusic(MultipartFile file, String username) {
        User user = userService.getUserByUsername(username);
        fsStorage.store(file, username);
        databaseStorage.save(file, user);
    }
}
