package com.example.demo.controller;

import java.util.ArrayList;

public class InvalidInputException extends RuntimeException {

    private static final long serialVersionUID = -5027121014723838738L;

    private ArrayList<JsonException> errors;

    public ArrayList<JsonException> getErrors() {
        return this.errors;
    }

    public InvalidInputException(ArrayList<JsonException> errors) {
        super();
        this.errors = errors;
    }

    public InvalidInputException(String message, ArrayList<JsonException> errors) {
        super(message);
        this.errors = errors;
    }

}