package com.alex.springmusicproject.controller;

import com.alex.springmusicproject.entity.Music;
import com.alex.springmusicproject.service.MusicService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final MusicService service;

    public AuthController(MusicService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Principal principal) {
        service.saveMusic(file, principal.getName());

        return "redirect:/auth/play";
    }

    @GetMapping
    public String auth() {
        return "auth-page";
    }

    @GetMapping("/play")
    public String play(Principal principal, Model model) {
        List<Music> musicList = service.getMusicList(principal.getName());
        model.addAttribute("musicList", musicList);

        return "play-page";
    }

    @CrossOrigin("*")
    @GetMapping("/music/{id}")
    public ResponseEntity<Resource> music(Principal principal, @PathVariable String id) {
        Resource file = service.loadMusic(principal.getName(), id);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(
                                file.getFilename()).
                        orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(file);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> image(Principal principal, @PathVariable String id) {
        byte[] file = service.loadImage(principal.getName(), id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(file);
    }
}
