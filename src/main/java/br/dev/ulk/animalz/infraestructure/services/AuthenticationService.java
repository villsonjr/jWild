package br.dev.ulk.animalz.infraestructure.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.dev.ulk.animalz.application.exceptions.AuthenticationFailedException;
import br.dev.ulk.animalz.application.exceptions.InvalidCredentialsException;
import br.dev.ulk.animalz.application.exceptions.ResourceAlreadyRegisteredException;
import br.dev.ulk.animalz.application.v1.payloads.AuthResponse;
import br.dev.ulk.animalz.application.v1.payloads.UserRequestDTO;
import br.dev.ulk.animalz.application.v1.payloads.dtos.AuthenticationDTO;
import br.dev.ulk.animalz.application.v1.payloads.dtos.UserDTO;
import br.dev.ulk.animalz.domain.enumerations.RoleEnum;
import br.dev.ulk.animalz.domain.enumerations.StatusEnum;
import br.dev.ulk.animalz.domain.models.Role;
import br.dev.ulk.animalz.domain.models.User;
import br.dev.ulk.animalz.infraestructure.configurations.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public UserDTO register(UserRequestDTO dto) {
        if (userService.isRegistered(dto)) {
            throw new ResourceAlreadyRegisteredException("User already registered");
        }
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(hashPassword(dto.getPassword()))
                .roles(new HashSet<>())
                .status(StatusEnum.ACTIVE)
                .build();
        Role userRole = roleService.findByRole(RoleEnum.USER);
        user.setRoles(Collections.singleton(userRole));
        return userService.save(user);
    }

    public AuthResponse authenticate(AuthenticationDTO authRequest) {
        Authentication auth = authenticateUser(authRequest);
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = userService.loadUserByUsername(authRequest.getUsername());
        return AuthResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .build();
    }

    private Authentication authenticateUser(AuthenticationDTO authRequest) {
        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
        } catch (InvalidCredentialsException | BadCredentialsException | UsernameNotFoundException ex) {
            throw new AuthenticationFailedException("Invalid username or password");
        }
    }

    private String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
}