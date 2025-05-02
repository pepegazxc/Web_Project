package org.example.web_project.ExeptionHandler;

import org.example.web_project.Exceptions.UserNotFound;
import org.example.web_project.Exceptions.UserWithThatDataAlreadyExist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFound userNotFound) {
        Map<String, String> response = new HashMap<String, String>();
        response.put("message", userNotFound.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserWithThatDataAlreadyExist.class)
    public ResponseEntity<Map<String, String>> handleUserWithThatDataAlreadyExist(UserWithThatDataAlreadyExist userWithThatDataAlreadyExist) {
        Map<String, String> response = new HashMap<>();
        response.put("message", userWithThatDataAlreadyExist.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
