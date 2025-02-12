package br.dev.ulk.animalz.application.exceptions;

public class InvalidHeaderException extends RuntimeException {
    public InvalidHeaderException(String message) {
        super(message);
    }
}