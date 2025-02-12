package br.dev.ulk.animalz.application.v1.controllers;

import br.dev.ulk.animalz.application.v1.payloads.APIResponse;
import br.dev.ulk.animalz.application.v1.payloads.dtos.AnimalDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AnimalControllerV1 {

    ResponseEntity<APIResponse<List<AnimalDTO>>> getAllAnimals();
    ResponseEntity<APIResponse<AnimalDTO>> getAnimalById(Long id);
    ResponseEntity<APIResponse<List<AnimalDTO>>> getAnimalsByGroup(String group);
    ResponseEntity<APIResponse<AnimalDTO>> createAnimal(AnimalDTO animalDTO);
    ResponseEntity<APIResponse<AnimalDTO>> updateAnimal(Long id, AnimalDTO animalDTO);
    ResponseEntity<APIResponse<AnimalDTO>> partialUpdateAnimal(Long id, AnimalDTO animalDTO);
    ResponseEntity<APIResponse<Boolean>> deleteAnimal(Long id);

}