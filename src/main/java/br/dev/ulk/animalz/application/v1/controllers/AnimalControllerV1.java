package br.dev.ulk.animalz.application.v1.controllers;

import br.dev.ulk.animalz.application.v1.payloads.APIResponse;
import br.dev.ulk.animalz.application.v1.payloads.dtos.AnimalDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/v1/animals")
@Tag(name = "Animals", description = "Animal management Endpoint")
public interface AnimalControllerV1 {

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
    ResponseEntity<APIResponse<List<AnimalDTO>>> getAllAnimals();

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
    ResponseEntity<APIResponse<AnimalDTO>> getAnimalById(Long id);

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
    ResponseEntity<APIResponse<List<AnimalDTO>>> getAnimalsByGroup(String group);

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
    ResponseEntity<APIResponse<AnimalDTO>> createAnimal(AnimalDTO animalDTO);

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
    ResponseEntity<APIResponse<AnimalDTO>> updateAnimal(Long id, AnimalDTO animalDTO);

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
    ResponseEntity<APIResponse<AnimalDTO>> partialUpdateAnimal(Long id, AnimalDTO animalDTO);

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
    ResponseEntity<APIResponse<Boolean>> deleteAnimal(Long id);
}