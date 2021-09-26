package com.alex.springmusicproject.service.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

public interface FsStorageService {
    void store(MultipartFile file, String username);
    void store(BufferedImage image, String musicName, String username);
    void deleteAll();
    Resource loadMusic(String username, String filename);
    byte[] loadImage(String username, String musicName);
}
