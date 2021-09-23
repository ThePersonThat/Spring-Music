package com.alex.springmusicproject.controller;

import com.alex.springmusicproject.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final StorageService storage;

    @Autowired
    public AuthController(StorageService storage) {
        this.storage = storage;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        storage.store(file);
        return "redirect:/";
    }

    @GetMapping
    public String auth() {
        return "auth-page";
    }

    @GetMapping("/play")
    public String play() {
        return "play-page";
    }

    @GetMapping("/music")
    public ResponseEntity<Resource> music() {
        Resource file = storage.loadResource();

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(
                        file.getFilename()).
                        orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(file);
    }
}
