package com.alex.springmusicproject.excpetion;

public class UserFolderNotFoundException extends RuntimeException {
    public UserFolderNotFoundException(String message) {
        super(message);
    }
}
