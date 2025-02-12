package br.dev.ulk.animalz.infraestructure.configurations.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PwdEncoder implements org.springframework.security.crypto.password.PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        String password = rawPassword.toString();
        return BCrypt.withDefaults()
                .hashToString(12, password.toCharArray());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String password = rawPassword.toString();
        return BCrypt.verifyer()
                .verify(password.toCharArray(), encodedPassword).verified;
    }

}
