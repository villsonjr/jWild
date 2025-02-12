package br.dev.ulk.animalz.application.v1.payloads.dtos;

import br.dev.ulk.animalz.domain.models.User;
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
public class UserDTO extends RepresentationModel<UserDTO> {

    private String name;
    private String email;
    private String username;
    private String status;
    private String[] roles;

    public UserDTO(User user) {
        name = user.getName();
        email = user.getEmail();
        username = user.getUsername();
        status = user.getStatus().getDescription();
        roles = user.getRoles().stream()
                .map(role -> role.getRole().getDescription())
                .toArray(String[]::new);
    }

    public static UserDTO fromEntity(User user) {
        return new UserDTO(user);
    }
}
