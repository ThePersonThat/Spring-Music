package com.alex.springmusicproject.repo;

import com.alex.springmusicproject.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
