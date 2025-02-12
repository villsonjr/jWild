package br.dev.ulk.animalz.infraestructure.repositories;

import br.dev.ulk.animalz.domain.models.Group;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends AbstractRepository<Group, Long> {

    Optional<Group> findByNameIgnoreCase(String name);

}
