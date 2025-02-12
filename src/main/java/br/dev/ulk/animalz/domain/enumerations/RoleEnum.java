package br.dev.ulk.animalz.domain.enumerations;

import br.dev.ulk.animalz.application.exceptions.InvalidRoleException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RoleEnum {

    USER("USER"),
    MODERATOR("MODERATOR"),
    ADMINISTRATOR("ADMINISTRATOR");

    private String description;

    @JsonCreator
    public static RoleEnum fromString(String s) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.description.equalsIgnoreCase(s)) {
                return role;
            }
        }
        throw new InvalidRoleException("Invalid role: " + s);
    }

}