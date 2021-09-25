package com.alex.springmusicproject.excpetion;

public class MusicNotFoundException extends RuntimeException {
    public MusicNotFoundException(String msg) {
        super(msg);
    }
}
