package com.alex.springmusicproject.service.storage;

import com.alex.springmusicproject.entity.Music;
import com.alex.springmusicproject.entity.User;
import com.alex.springmusicproject.repo.MusicRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DataBaseStorageService {
    private final MusicRepo musicRepo;
    private final Logger logger = LoggerFactory.getLogger(DataBaseStorageService.class);

    public DataBaseStorageService(MusicRepo musicRepo) {
        this.musicRepo = musicRepo;
    }

    @Transactional
    public void save(Music music, User user) {
        user.getMusicList().add(music);
        musicRepo.save(music);
        logger.info("The data of the music: " + music.getFilename() + " was saved to the database");
    }
}
