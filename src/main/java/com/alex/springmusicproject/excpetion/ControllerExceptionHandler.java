package com.alex.springmusicproject.excpetion;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserFolderNotFoundException.class)
    public ResponseEntity<String> handleFolderNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFound() {
        return ResponseEntity.notFound().build();
    }
}
