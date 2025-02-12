package br.dev.ulk.animalz.infraestructure.configurations.security;

import br.dev.ulk.animalz.application.exceptions.AuthenticationFailedException;
import br.dev.ulk.animalz.application.exceptions.InvalidHeaderException;
import br.dev.ulk.animalz.application.exceptions.InvalidTokenException;
import br.dev.ulk.animalz.domain.models.User;
import br.dev.ulk.animalz.infraestructure.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Arrays;

import static br.dev.ulk.animalz.domain.constants.SystemConstants.HEADER_STRING;
import static br.dev.ulk.animalz.domain.constants.SystemConstants.TOKEN_INDEX;
import static br.dev.ulk.animalz.domain.constants.SystemConstants.TOKEN_PREFIX;
import static br.dev.ulk.animalz.domain.constants.SystemConstants.WHITE_LIST_URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final RequestMatcher requestMatcher = new OrRequestMatcher(
            Arrays.stream(WHITE_LIST_URL)
                    .map(AntPathRequestMatcher::new)
                    .toArray(AntPathRequestMatcher[]::new)
    );

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            if (!requestMatcher.matches(request)) {
                validateTokenAndAuthenticate(request);
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.info("{} | {} | {}", ex.getMessage(), request.getMethod(), request.getRequestURI());
            resolver.resolveException(request, response, null, ex);
        }
    }

    private void validateTokenAndAuthenticate(HttpServletRequest request) {
        String authHeader = request.getHeader(HEADER_STRING);
        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX) || authHeader.split(TOKEN_PREFIX)[1].trim().isEmpty()) {
            throw new InvalidHeaderException("Invalid Authorization Header");
        }
        String jwtToken = authHeader.substring(TOKEN_INDEX);
        if (!jwtService.isTokenValid(jwtToken)) {
            throw new InvalidTokenException("Invalid Token");
        }
        String username = jwtService.extractUsername(jwtToken);
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new AuthenticationFailedException("Authentication Failed"));
        log.info("User: {} | Token: {} | {} | {}", username, jwtToken, request.getMethod(), request.getRequestURI());
        authenticateUser(user, request);
    }

    private void authenticateUser(User user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}