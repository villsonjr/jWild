package br.dev.ulk.animalz.application.v1.controllers.implementations;

import br.dev.ulk.animalz.application.exceptions.ResourceNotFoundException;
import br.dev.ulk.animalz.application.v1.controllers.GroupControllerV1;
import br.dev.ulk.animalz.application.v1.payloads.APIResponse;
import br.dev.ulk.animalz.application.v1.payloads.dtos.GroupDTO;
import br.dev.ulk.animalz.infraestructure.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class GroupControllerV1Impl implements GroupControllerV1 {

    @Autowired
    private GroupService groupService;

    @Override
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