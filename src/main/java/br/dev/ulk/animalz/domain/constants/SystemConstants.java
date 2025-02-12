package br.dev.ulk.animalz.domain.constants;

public final class SystemConstants {

    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final Integer TOKEN_INDEX = 7;
    public static final String[] WHITE_LIST_URL = {
            // PUBLIC
            "/v1/public/**",
            // AUTHENTICATION END POINTS
            "/v1/auth/sign-up",
            "/v1/auth/sign-in",
            "/v1/auth/sign-out",
            // DOCUMENTATIONS
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            // SECURITY AND CONFIGURATIONS
            "/configuration/ui",
            "/configuration/security",
            // WEB JARS
            "/webjars/**"
    };

}