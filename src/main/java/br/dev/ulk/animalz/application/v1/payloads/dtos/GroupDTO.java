package br.dev.ulk.animalz.application.v1.payloads.dtos;

import br.dev.ulk.animalz.domain.models.Group;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class GroupDTO extends RepresentationModel<GroupDTO> {

    @Schema(
            description = "ID of the group",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Name of the group",
            example = "Mammals",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    public GroupDTO(Group group) {
        id = group.getId();
        name = group.getName();
    }

    public static GroupDTO fromEntity(Group group) {
        return new GroupDTO(group);
    }
}
