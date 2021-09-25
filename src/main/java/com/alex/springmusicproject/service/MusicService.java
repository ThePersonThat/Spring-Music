package com.alex.springmusicproject.service;

import com.alex.springmusicproject.entity.Music;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MusicService {
    void saveMusic(MultipartFile file, String username);
    List<Music> getMusicList(String username);
    Resource loadMusic(String username, String id);
}
