package com.alex.springmusicproject.repo;

import com.alex.springmusicproject.entity.Music;
import org.springframework.data.repository.CrudRepository;

public interface MusicRepo extends CrudRepository<Music, Long> {
}
