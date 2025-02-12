package br.dev.ulk.animalz.application.exceptions;

public class ResourceAlreadyRegisteredException extends RuntimeException {
    public ResourceAlreadyRegisteredException(String message) {
        super(message);
    }
}