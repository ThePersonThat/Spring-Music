package com.alex.springmusicproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/auth/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("FILENAME = " + file.getOriginalFilename());
        return "redirect:/";
    }

    @GetMapping("/auth")
    public String auth() {
        return "auth-page";
    }
}
