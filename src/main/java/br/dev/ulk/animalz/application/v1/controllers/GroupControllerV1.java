package br.dev.ulk.animalz.application.v1.controllers;

import br.dev.ulk.animalz.application.v1.payloads.APIResponse;
import br.dev.ulk.animalz.application.v1.payloads.dtos.GroupDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface GroupControllerV1 {

    ResponseEntity<APIResponse<List<GroupDTO>>> getAllGroups();
    ResponseEntity<APIResponse<GroupDTO>> getGroupById(@PathVariable Long id);
    ResponseEntity<APIResponse<GroupDTO>> createGroup(@RequestBody GroupDTO groupDTO);
    ResponseEntity<APIResponse<GroupDTO>> updateGroup(@PathVariable Long id, @RequestBody GroupDTO groupDTO);
    ResponseEntity<APIResponse<Boolean>> deleteGroup(@PathVariable Long id);

}