package br.dev.ulk.animalz.application.controllers;

import br.dev.ulk.animalz.application.dtos.GroupDTO;
import br.dev.ulk.animalz.infraestructure.services.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/groups")
@Tag(name = "Groups", description = "Groups management Endpoint")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    @Operation(
            summary = "Returns all groups",
            description = "This endpoint returns a list of all registered groups.",
            tags = {"Groups", "z1 - Get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of groups successfully returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupDTO.class))),
            @ApiResponse(responseCode = "204", description = "No groups found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        List<GroupDTO> groups = groupService.getAllGroups();
        if (groups.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Returns a group by ID",
            description = "This endpoint returns the details of a specific group by the given ID.",
            tags = {"Groups", "z1 - Get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Group found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupDTO.class))),
            @ApiResponse(responseCode = "404", description = "Group not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @PostMapping
    @Operation(
            summary = "Create a new group",
            description = "This endpoint creates a new group based on the provided information.",
            tags = {"Groups", "z2 - Post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Group successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<GroupDTO> createGroup(@RequestBody GroupDTO groupDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.createGroup(groupDTO));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing group",
            description = "This endpoint updates an existing group identified by the given ID.",
            tags = {"Groups", "z3 - Put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Group successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Group not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable Long id, @RequestBody GroupDTO groupDTO) {
        return ResponseEntity.ok(groupService.updateGroup(id, groupDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a group",
            description = "This endpoint deletes a group by the given ID.",
            tags = {"Groups", "z5 - Delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Group successfully deleted", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Group not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
