package com.alex.springmusicproject.service;

import com.alex.springmusicproject.entity.User;

public interface UserService {
    void saveUser(User user);
    User getUserByUsername(String username);
}
