package org.example.web_project.Exceptions;

public class NoAccessToEditing extends RuntimeException {
    public NoAccessToEditing(String message) {
        super(message);
    }
}
