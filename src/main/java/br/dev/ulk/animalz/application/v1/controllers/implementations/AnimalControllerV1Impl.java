package br.dev.ulk.animalz.application.v1.controllers.implementations;

import br.dev.ulk.animalz.application.exceptions.ResourceNotFoundException;
import br.dev.ulk.animalz.application.v1.controllers.AnimalControllerV1;
import br.dev.ulk.animalz.application.v1.payloads.APIResponse;
import br.dev.ulk.animalz.application.v1.payloads.dtos.AnimalDTO;
import br.dev.ulk.animalz.infraestructure.services.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/animals")
@Tag(name = "Animals", description = "Animal management Endpoint")
public class AnimalControllerV1Impl implements AnimalControllerV1 {

    @Autowired
    private AnimalService animalService;

    @Override
    @GetMapping
    @Operation(
            summary = "Returns  all Animals",
            description = "This endpoint retrun a list of all registered animals.",
            tags = {"Animals", "z1 - Get"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of animals successfully returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnimalDTO.class))),
                    @ApiResponse(responseCode = "204", description = "No animals found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
            }
    )
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
    @GetMapping("/{id}")
    @Operation(
            summary = "Returns an animal by ID",
            description = "This endpoint returns the details of a specific animal by the given ID.",
            tags = {"Animals", "z1 - Get"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Animal found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnimalDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Data validation error", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Animal not found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
            }
    )
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
    @GetMapping("/groups/{group}")
    @Operation(
            summary = "Returns animals by group",
            description = "This endpoint returns all animals belonging to the given group.",
            tags = {"Animals", "z1 - Get"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of animals in the group successfully returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnimalDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Data validation error", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Group not found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
            }
    )
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
    @PostMapping
    @Operation(
            summary = "Creates a new animal",
            description = "This endpoint creates a new animal from the provided information.",
            tags = {"Animals", "z2 - Post"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Animal created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnimalDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Data validation error", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Data validation error", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
            }
    )
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
    @PutMapping("/{id}")
    @Operation(
            summary = "Updates an existing animal",
            description = "This endpoint updates an existing animal's information, identified by the given ID.",
            tags = {"Animals", "z3 - Put"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Animal updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnimalDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Data validation error", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Animal not found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
            }
    )
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
    @PatchMapping("/{id}")
    @Operation(
            summary = "Partially updates an animal",
            description = "This endpoint allows partial updates of an existing animal's information.",
            tags = {"Animals", "z4 - Patch"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Animal partially updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnimalDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Data validation error", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Animal not found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
            }
    )
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
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletes an animal",
            description = "This endpoint deletes an animal identified by the given ID.",
            tags = {"Animals", "z5 - Delete"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Animal deleted successfully", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Data validation error", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Animal not found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
            }
    )
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