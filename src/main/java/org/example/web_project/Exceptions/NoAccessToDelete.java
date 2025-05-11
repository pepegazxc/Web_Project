package org.example.web_project.Exceptions;

public class NoAccessToDelete extends RuntimeException {
    public NoAccessToDelete(String message) {
        super(message);
    }
}
