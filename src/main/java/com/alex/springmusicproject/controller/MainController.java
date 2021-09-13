package com.alex.springmusicproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {

    @GetMapping("/")
    public String home() {
        return "home-page";
    }

    @GetMapping("/auth")
    public String auth() {
        return "auth-page";
    }

    @GetMapping("/login")
    public String login() {
        return "login-page";
    }
}
