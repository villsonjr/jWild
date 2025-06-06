package br.dev.ulk.animalz.domain.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum StatusEnum {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private String description;
}
