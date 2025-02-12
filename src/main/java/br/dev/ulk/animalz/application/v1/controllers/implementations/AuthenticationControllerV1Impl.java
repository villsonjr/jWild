package br.dev.ulk.animalz.application.v1.controllers.implementations;

import br.dev.ulk.animalz.application.v1.controllers.AuthenticationControllerV1;
import br.dev.ulk.animalz.application.v1.payloads.APIResponse;
import br.dev.ulk.animalz.application.v1.payloads.AuthResponse;
import br.dev.ulk.animalz.application.v1.payloads.UserRequestDTO;
import br.dev.ulk.animalz.application.v1.payloads.dtos.AuthenticationDTO;
import br.dev.ulk.animalz.application.v1.payloads.dtos.UserDTO;
import br.dev.ulk.animalz.infraestructure.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/auth")
@Tag(name = "Authentication", description = "Authentication Endpoint")
public class AuthenticationControllerV1Impl implements AuthenticationControllerV1 {

    @Autowired
    private AuthenticationService authService;

    @Override
    @PostMapping("/sign-up")
    @Operation(
            summary = "Register a new user",
            description = "This endpoint allows a new user to register by providing necessary information.",
            tags = {"Users", "z2 - Post"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "User successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "409", description = "Conflict - Email or username already exists", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
            }
    )
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
    @PostMapping("/sign-in")
    @Operation(
            summary = "User Sign-In",
            description = "This endpoint allows users to authenticate by providing their credentials (username/email and password). Upon successful authentication, a token will be returned for future requests that require authentication. This token must be included in the authorization header for secure access to protected resources.",
            tags = {"Users", "z2 - Post"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully signed in", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials", content = @Content(mediaType = "application/json")), @ApiResponse(responseCode = "404", description = "Group not found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input data", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
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