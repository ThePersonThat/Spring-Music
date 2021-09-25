package com.alex.springmusicproject.service.storage;

import com.alex.springmusicproject.excpetion.UserFolderNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

public interface FsStorageService {
    void store(MultipartFile file, String username);
    void store(BufferedImage image, String username);
    void deleteAll();
    Resource loadMusic(String username, String filename);
    Resource loadImage(String username);
}
