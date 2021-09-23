package com.alex.springmusicproject.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void store(MultipartFile file);
    void deleteAll();
    Resource loadResource();
}
