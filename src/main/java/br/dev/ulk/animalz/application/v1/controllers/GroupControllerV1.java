package br.dev.ulk.animalz.application.v1.controllers;

import br.dev.ulk.animalz.application.v1.payloads.APIResponse;
import br.dev.ulk.animalz.application.v1.payloads.dtos.GroupDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/v1/groups")
@Tag(name = "Groups", description = "Groups management Endpoint")
public interface GroupControllerV1 {

    @GetMapping
    @Operation(
            summary = "Returns all groups",
            description = "This endpoint returns a list of all registered groups.",
            tags = {"Groups", "z1 - Get"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of groups successfully returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupDTO.class))),
                    @ApiResponse(responseCode = "204", description = "No groups found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
            }
    )
    ResponseEntity<APIResponse<List<GroupDTO>>> getAllGroups();

    @GetMapping("/{id}")
    @Operation(
            summary = "Returns a group by ID",
            description = "This endpoint returns the details of a specific group by the given ID.",
            tags = {"Groups", "z1 - Get"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Group found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Group not found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
            }
    )
    ResponseEntity<APIResponse<GroupDTO>> getGroupById(@PathVariable Long id);

    @PostMapping
    @Operation(
            summary = "Create a new group",
            description = "This endpoint creates a new group based on the provided information.",
            tags = {"Groups", "z2 - Post"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Group successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
            }
    )
    ResponseEntity<APIResponse<GroupDTO>> createGroup(@RequestBody GroupDTO groupDTO);

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing group",
            description = "This endpoint updates an existing group identified by the given ID.",
            tags = {"Groups", "z3 - Put"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Group successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Group not found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
            }
    )
    ResponseEntity<APIResponse<GroupDTO>> updateGroup(@PathVariable Long id, @RequestBody GroupDTO groupDTO);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a group",
            description = "This endpoint deletes a group by the given ID.",
            tags = {"Groups", "z5 - Delete"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Group successfully deleted", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Group not found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
            }
    )
    ResponseEntity<APIResponse<Boolean>> deleteGroup(@PathVariable Long id);
}