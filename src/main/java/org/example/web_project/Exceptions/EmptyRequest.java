package org.example.web_project.Exceptions;

public class EmptyRequest extends RuntimeException {
    public EmptyRequest(String message) {
        super(message);
    }
}
