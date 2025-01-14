package br.dev.ulk.animalz.application.controllers;

import br.dev.ulk.animalz.application.dtos.AnimalDTO;
import br.dev.ulk.animalz.infraestructure.services.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/animals")
@Tag(name = "Animals", description = "Animal management Endpoint")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @GetMapping
    @Operation(
            summary = "Returns  all Animals",
            description = "This endpoint retrun a list of all registered animals.",
            tags = {"Animals", "z1 - Get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of animals successfully returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnimalDTO.class))),
            @ApiResponse(responseCode = "204", description = "No animals found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<AnimalDTO>> getAllAnimals() {
        List<AnimalDTO> animals = animalService.getAllAnimals();
        if (animals.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(animals);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Returns an animal by ID",
            description = "This endpoint returns the details of a specific animal by the given ID.",
            tags = {"Animals", "z1 - Get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Animal found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnimalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Animal not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<AnimalDTO> getAnimalById(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.getAnimalById(id));
    }

    @GetMapping("/groups/{group}")
    @Operation(
            summary = "Returns animals by group",
            description = "This endpoint returns all animals belonging to the given group.",
            tags = {"Animals", "z1 - Get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of animals in the group successfully returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnimalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Group not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<AnimalDTO>> getAnimalsByGroup(@PathVariable String group) {
        return ResponseEntity.ok(animalService.getAnimalsByGroup(group));
    }

    @PostMapping
    @Operation(
            summary = "Creates a new animal",
            description = "This endpoint creates a new animal from the provided information.",
            tags = {"Animals", "z2 - Post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Animal created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnimalDTO.class))),
            @ApiResponse(responseCode = "400", description = "Data validation error", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<AnimalDTO> createAnimal(@Valid @RequestBody AnimalDTO animalDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(animalService.createAnimal(animalDTO));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Updates an existing animal",
            description = "This endpoint updates an existing animal's information, identified by the given ID.",
            tags = {"Animals", "z3 - Put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Animal updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnimalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Animal not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Data validation error", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<AnimalDTO> updateAnimal(@PathVariable Long id, @Valid @RequestBody AnimalDTO animalDTO) {
        return ResponseEntity.ok(animalService.updateAnimal(id, animalDTO));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Partially updates an animal",
            description = "This endpoint allows partial updates of an existing animal's information.",
            tags = {"Animals", "z4 - Patch"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Animal partially updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnimalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Animal not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<AnimalDTO> partialUpdateAnimal(@PathVariable Long id, @Valid @RequestBody AnimalDTO animalDTO) {
        return ResponseEntity.ok(animalService.partialUpdateAnimal(id, animalDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletes an animal",
            description = "This endpoint deletes an animal identified by the given ID.",
            tags = {"Animals", "z5 - Delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Animal deleted successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Animal not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
