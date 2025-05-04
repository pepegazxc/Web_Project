package org.example.web_project.Exceptions;

public class EmptyStorage extends RuntimeException {
    public EmptyStorage(String message) {
        super(message);
    }
}
