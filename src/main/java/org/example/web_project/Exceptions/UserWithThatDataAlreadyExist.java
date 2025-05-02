package org.example.web_project.Exceptions;

public class UserWithThatDataAlreadyExist extends RuntimeException {
    public UserWithThatDataAlreadyExist(String message) {
        super(message);
    }
}
