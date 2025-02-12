package br.dev.ulk.animalz.application.v1.controllers;

import br.dev.ulk.animalz.application.v1.payloads.APIResponse;
import br.dev.ulk.animalz.application.v1.payloads.AuthResponse;
import br.dev.ulk.animalz.application.v1.payloads.UserRequestDTO;
import br.dev.ulk.animalz.application.v1.payloads.dtos.AuthenticationDTO;
import br.dev.ulk.animalz.application.v1.payloads.dtos.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationControllerV1 {

    ResponseEntity<APIResponse<UserDTO>> register(@RequestBody UserRequestDTO dto);
    ResponseEntity<APIResponse<AuthResponse>> authenticate(@RequestBody AuthenticationDTO authRequest);

}