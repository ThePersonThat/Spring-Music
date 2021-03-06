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

    @GetMapping("/login")
    public String login(Principal principal) {
        if (principal == null) {
            return "login-page";
        }

        return "redirect:/auth";
    }
}
