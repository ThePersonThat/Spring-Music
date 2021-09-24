package com.alex.springmusicproject.service.storage;

import com.alex.springmusicproject.excpetion.UserFolderNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FsStorageService {
    void store(MultipartFile file, String username);
    void deleteAll();
    Resource loadResource(String username) throws UserFolderNotFoundException;
}
