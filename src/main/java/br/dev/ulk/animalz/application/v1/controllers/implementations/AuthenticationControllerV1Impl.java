package br.dev.ulk.animalz.application.v1.controllers.implementations;

import br.dev.ulk.animalz.application.v1.controllers.AuthenticationControllerV1;
import br.dev.ulk.animalz.application.v1.payloads.APIResponse;
import br.dev.ulk.animalz.application.v1.payloads.AuthResponse;
import br.dev.ulk.animalz.application.v1.payloads.UserRequestDTO;
import br.dev.ulk.animalz.application.v1.payloads.dtos.AuthenticationDTO;
import br.dev.ulk.animalz.application.v1.payloads.dtos.UserDTO;
import br.dev.ulk.animalz.infraestructure.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class AuthenticationControllerV1Impl implements AuthenticationControllerV1 {

    @Autowired
    private AuthenticationService authService;

    @Override
    public ResponseEntity<APIResponse<UserDTO>> register(@RequestBody UserRequestDTO dto) {
        UserDTO userDTO = authService.register(dto);
        APIResponse<UserDTO> response = APIResponse.<UserDTO>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.CREATED.value())
                .message("User successfully created")
                .payload(userDTO)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<APIResponse<AuthResponse>> authenticate(@RequestBody AuthenticationDTO authRequest) {
        AuthResponse authResponse = authService.authenticate(authRequest);
        APIResponse<AuthResponse> response = APIResponse.<AuthResponse>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .message("Successfully signed in")
                .payload(authResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}