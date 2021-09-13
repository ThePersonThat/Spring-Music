package com.alex.springmusicproject;

import com.alex.springmusicproject.entity.User;
import com.alex.springmusicproject.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SpringMusicProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMusicProjectApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(UserService userService) {
        return args -> userService.saveUser(new User(0L, "alex", "root"));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
