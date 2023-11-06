package com.childsplay.pawfectly.exception.custom;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class FieldValidationException extends RuntimeException {
    private Map<String, Object> errors;

    public FieldValidationException(String message, Map<String, Object> errors) {
        super(message);
        this.errors = errors;
    }
}