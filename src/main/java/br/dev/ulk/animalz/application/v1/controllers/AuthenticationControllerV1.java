package br.dev.ulk.animalz.application.v1.controllers;

import br.dev.ulk.animalz.application.v1.payloads.APIResponse;
import br.dev.ulk.animalz.application.v1.payloads.AuthResponse;
import br.dev.ulk.animalz.application.v1.payloads.UserRequestDTO;
import br.dev.ulk.animalz.application.v1.payloads.dtos.AuthenticationDTO;
import br.dev.ulk.animalz.application.v1.payloads.dtos.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*")
@RequestMapping("/v1/auth")
@Tag(name = "Authentication", description = "Authentication Endpoint")
public interface AuthenticationControllerV1 {

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
    ResponseEntity<APIResponse<UserDTO>> register(@RequestBody UserRequestDTO dto);

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
    ResponseEntity<APIResponse<AuthResponse>> authenticate(@RequestBody AuthenticationDTO authRequest);

}