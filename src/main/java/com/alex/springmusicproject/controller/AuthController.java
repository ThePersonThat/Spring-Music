package com.alex.springmusicproject.controller;

import com.alex.springmusicproject.excpetion.UserFolderNotFoundException;
import com.alex.springmusicproject.service.MusicService;
import com.alex.springmusicproject.service.storage.FsStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final MusicService service;

    @Autowired
    public AuthController(MusicService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Principal principal) {
        service.saveMusic(file, principal.getName());

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

 /*   @GetMapping("/music")
    public ResponseEntity<Resource> music(Principal principal) throws UserFolderNotFoundException {
        Resource file = service.loadResource(principal.getName());

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(
                                file.getFilename()).
                        orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(file);
    }*/
}
