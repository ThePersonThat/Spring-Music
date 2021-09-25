package com.alex.springmusicproject.service.storage;

import com.alex.springmusicproject.entity.Music;
import com.alex.springmusicproject.entity.User;

public interface DatabaseStorageService {
    void save(Music music, User user);
}
