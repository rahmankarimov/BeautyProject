package com.beautyProject.beautyProject.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ValidationErrorDetails extends ErrorDetails {
    private List<String> errors;

    public ValidationErrorDetails(Date timestamp, int status, String error, String message, String path, List<String> errors) {
        super(timestamp, status, error, message, path);
        this.errors = errors;
    }
}