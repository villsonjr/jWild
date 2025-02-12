package br.dev.ulk.animalz.infraestructure.repositories;

import br.dev.ulk.animalz.domain.enumerations.RoleEnum;
import br.dev.ulk.animalz.domain.models.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends AbstractRepository<Role, Long> {

    Optional<Role> findByRole(RoleEnum role);

}
