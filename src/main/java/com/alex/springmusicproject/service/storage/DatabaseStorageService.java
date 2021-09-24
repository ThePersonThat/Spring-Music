package com.alex.springmusicproject.service.storage;

import com.alex.springmusicproject.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface DatabaseStorageService {
    void save(MultipartFile file, User user);
}
