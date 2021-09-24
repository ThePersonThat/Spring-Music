package com.alex.springmusicproject.service;

import org.springframework.web.multipart.MultipartFile;

public interface MusicService {
    void saveMusic(MultipartFile file, String username);
}
