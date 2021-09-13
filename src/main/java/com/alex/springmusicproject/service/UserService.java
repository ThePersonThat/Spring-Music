package com.alex.springmusicproject.service;

import com.alex.springmusicproject.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    void saveUser(User user);
    User getUserByUsername(String username) throws UsernameNotFoundException;
}
