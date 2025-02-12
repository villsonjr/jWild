package br.dev.ulk.animalz.application.v1.controllers.implementations;

import br.dev.ulk.animalz.application.exceptions.ResourceNotFoundException;
import br.dev.ulk.animalz.application.v1.controllers.GroupControllerV1;
import br.dev.ulk.animalz.application.v1.payloads.APIResponse;
import br.dev.ulk.animalz.application.v1.payloads.dtos.GroupDTO;
import br.dev.ulk.animalz.infraestructure.services.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/groups")
@Tag(name = "Groups", description = "Groups management Endpoint")
public class GroupControllerV1Impl implements GroupControllerV1 {

    @Autowired
    private GroupService groupService;

    @Override
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
    public ResponseEntity<APIResponse<List<GroupDTO>>> getAllGroups() {
        List<GroupDTO> groups = groupService.getAllGroups();
        if (groups.isEmpty()) {
            throw new ResourceNotFoundException("No groups found");
        }
        APIResponse<List<GroupDTO>> response = APIResponse.<List<GroupDTO>>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .message("List of groups successfully returned")
                .payload(groups)
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
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
    public ResponseEntity<APIResponse<GroupDTO>> getGroupById(@PathVariable Long id) {
        GroupDTO groupDTO = groupService.getGroupById(id);
        if (groupDTO == null) {
            throw new ResourceNotFoundException("Group not found");
        }
        APIResponse<GroupDTO> response = APIResponse.<GroupDTO>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .message("Group found")
                .payload(groupDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
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
    public ResponseEntity<APIResponse<GroupDTO>> createGroup(@RequestBody GroupDTO groupDTO) {
        GroupDTO createdGroup = groupService.createGroup(groupDTO);
        APIResponse<GroupDTO> response = APIResponse.<GroupDTO>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.CREATED.value())
                .message("Group successfully created")
                .payload(createdGroup)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
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
    public ResponseEntity<APIResponse<GroupDTO>> updateGroup(@PathVariable Long id, @RequestBody GroupDTO groupDTO) {
        GroupDTO updatedGroup = groupService.updateGroup(id, groupDTO);
        if (updatedGroup == null) {
            throw new ResourceNotFoundException("Group not found");
        }
        APIResponse<GroupDTO> response = APIResponse.<GroupDTO>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .message("Group successfully updated")
                .payload(updatedGroup)
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
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
    public ResponseEntity<APIResponse<Boolean>> deleteGroup(@PathVariable Long id) {
        boolean isDeleted = groupService.delete(id);
        if (!isDeleted) {
            throw new ResourceNotFoundException("Group not found");
        }
        APIResponse<Boolean> response = APIResponse.<Boolean>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.NO_CONTENT.value())
                .message("Group successfully deleted")
                .payload(true)
                .build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}