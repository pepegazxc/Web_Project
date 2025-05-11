package org.example.web_project.ExeptionHandler;

import org.example.web_project.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmptyRequest.class)
    public ResponseEntity<Map<String, String>> handleEmptyRequest(EmptyRequest emptyRequest) {
        Map<String, String> response = new HashMap<>();
        response.put("message", emptyRequest.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

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

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<Map<String, String>> handleTokenException(TokenException tokenException) {
        Map<String, String> response = new HashMap<>();
        response.put("message", tokenException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmptyStorage.class)
    public ResponseEntity<Map<String, String>> handleEmptyStorage(EmptyStorage emptyStorage) {
        Map<String, String> response = new HashMap<>();
        response.put("message", emptyStorage.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyID.class)
    public ResponseEntity<Map<String, String>> handleEmptyID(EmptyID emptyID) {
        Map<String, String> response = new HashMap<>();
        response.put("message", emptyID.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoAccessToEditing.class)
    public ResponseEntity<Map<String, String>> handleNoAccessToEditing(NoAccessToEditing method) {
        Map<String, String> response = new HashMap<>();
        response.put("message", method.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoAccessToDelete.class)
    public ResponseEntity<Map<String, String>> handleThereIsNoAccessToDelete(NoAccessToDelete method) {
        Map<String, String> response = new HashMap<>();
        response.put("message", method.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
