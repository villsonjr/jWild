package br.dev.ulk.animalz.application.exceptions;

public class InternalServerException extends RuntimeException {

    public InternalServerException(String message) {
        super(message);
    }

}
