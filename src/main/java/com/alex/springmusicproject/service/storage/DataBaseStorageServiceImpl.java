package com.alex.springmusicproject.service.storage;

import com.alex.springmusicproject.entity.Music;
import com.alex.springmusicproject.entity.User;
import com.alex.springmusicproject.repo.MusicRepo;
import com.alex.springmusicproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
public class DataBaseStorageServiceImpl implements DatabaseStorageService {
    private final MusicRepo musicRepo;

    @Autowired
    public DataBaseStorageServiceImpl(MusicRepo musicRepo) {
        this.musicRepo = musicRepo;
    }

    @Override
    @Transactional
    public void save(MultipartFile file, User user) {
        Music music = parseFile(file);
        user.getMusicList().add(music);
        musicRepo.save(music);
    }

    private Music parseFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String[] tokens = filename.split("-");
        String band = tokens[0];
        String songName = tokens[1];

        return new Music(band, songName, filename);
    }
}
