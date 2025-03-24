package br.dev.ulk.animalz.application.v1.controllers.implementations;

import br.dev.ulk.animalz.application.exceptions.ResourceNotFoundException;
import br.dev.ulk.animalz.application.v1.controllers.AnimalControllerV1;
import br.dev.ulk.animalz.application.v1.payloads.APIResponse;
import br.dev.ulk.animalz.application.v1.payloads.dtos.AnimalDTO;
import br.dev.ulk.animalz.infraestructure.services.AnimalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class AnimalControllerV1Impl implements AnimalControllerV1 {

    @Autowired
    private AnimalService animalService;

    @Override
    public ResponseEntity<APIResponse<List<AnimalDTO>>> getAllAnimals() {
        List<AnimalDTO> animals = animalService.getAllAnimals();
        if (animals.isEmpty()) {
            throw new ResourceNotFoundException("No animals found");
        }
        APIResponse<List<AnimalDTO>> response = APIResponse.<List<AnimalDTO>>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .message("Animals found successfully")
                .payload(animals)
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<APIResponse<AnimalDTO>> getAnimalById(@PathVariable Long id) {
        AnimalDTO animalDTO = animalService.getAnimalById(id);
        if (animalDTO == null) {
            throw new ResourceNotFoundException("Animal not found");
        }
        APIResponse<AnimalDTO> response = APIResponse.<AnimalDTO>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .message("Animal found successfully")
                .payload(animalService.getAnimalById(id))
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<APIResponse<List<AnimalDTO>>> getAnimalsByGroup(@PathVariable String group) {
        List<AnimalDTO> animals = animalService.getAnimalsByGroup(group);
        if (animals.isEmpty()) {
            throw new ResourceNotFoundException("Group not found");
        }
        APIResponse<List<AnimalDTO>> response = APIResponse.<List<AnimalDTO>>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .message("Animals found in the group")
                .payload(animals)
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<APIResponse<AnimalDTO>> createAnimal(@Valid @RequestBody AnimalDTO animalDTO) {
        AnimalDTO createdAnimal = animalService.createAnimal(animalDTO);
        APIResponse<AnimalDTO> response = APIResponse.<AnimalDTO>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.CREATED.value())
                .message("Animal created successfully")
                .payload(createdAnimal)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<APIResponse<AnimalDTO>> updateAnimal(@PathVariable Long id, @Valid @RequestBody AnimalDTO animalDTO) {
        AnimalDTO updatedAnimal = animalService.updateAnimal(id, animalDTO);
        if (updatedAnimal == null) {
            throw new ResourceNotFoundException("Animal not found");
        }
        APIResponse<AnimalDTO> response = APIResponse.<AnimalDTO>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .message("Animal updated successfully")
                .payload(updatedAnimal)
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<APIResponse<AnimalDTO>> partialUpdateAnimal(@PathVariable Long id, @Valid @RequestBody AnimalDTO animalDTO) {
        AnimalDTO updatedAnimal = animalService.partialUpdateAnimal(id, animalDTO);
        if (updatedAnimal == null) {
            throw new ResourceNotFoundException("Animal not found");
        }
        APIResponse<AnimalDTO> response = APIResponse.<AnimalDTO>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .message("Animal partially updated successfully")
                .payload(updatedAnimal)
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<APIResponse<Boolean>> deleteAnimal(@PathVariable Long id) {
        boolean isDeleted = animalService.delete(id);
        if (!isDeleted) {
            throw new ResourceNotFoundException("Animal not found");
        }
        APIResponse<Boolean> response = APIResponse.<Boolean>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.NO_CONTENT.value())
                .message("Animal deleted successfully")
                .payload(true)
                .build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}