package br.dev.ulk.animalz.application.v1.payloads.dtos;

import br.dev.ulk.animalz.domain.models.Animal;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDTO extends RepresentationModel<AnimalDTO> {

    @Schema(
            description = "ID of the animal",
            example = "1"
    )
    private Long id;

    @Valid
    @NotNull
    @Schema(
            description = "Scientific name of the animal",
            example = "Panthera leo",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String scientificName;

    @Valid
    @NotNull
    @Schema(
            description = "Species name of the animal",
            example = "Lion",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String specie;

    @Valid
    @NotNull
    @Schema(
            description = "Size of the animal in meters",
            example = "1.8",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Double size;

    @Valid
    @NotNull
    @Schema(
            description = "Mass of the animal in kilograms",
            example = "190.5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Double mass;

    @Schema(
            description = "Current  status of the animal",
            example = "ACTIVE",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String status;

    @Valid
    @NotNull
    @Schema(
            description = "Group information the animal belongs to",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private GroupDTO group;

    public AnimalDTO(Animal animal) {
        id = animal.getId();
        scientificName = animal.getScientificName();
        specie = animal.getSpecie();
        size = animal.getSize();
        mass = animal.getMass();
        status = animal.getStatus().getDescription();
        group = new GroupDTO(
                animal.getGroup().getId(),
                animal.getGroup().getName()
        );
    }

    public static AnimalDTO fromEntity(Animal animal) {
        return new AnimalDTO(animal);
    }
}
