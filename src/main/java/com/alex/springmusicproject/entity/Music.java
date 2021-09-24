package com.alex.springmusicproject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "musics")
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String band;
    private String name;
    private String filename;

    public Music() {}

    public Music(String band, String name, String filename) {
        this.band = band;
        this.name = name;
        this.filename = filename;
    }

    public String getBand() {
        return band;
    }

    public String getName() {
        return name;
    }

    public String getFilename() {
        return filename;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
