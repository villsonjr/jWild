package br.dev.ulk.animalz.infraestructure.repositories;

import br.dev.ulk.animalz.domain.models.Animal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends AbstractRepository<Animal, Long> {

    List<Animal> findAnimalsByGroupId(Long groupId);

    @Query("SELECT a FROM Animal a WHERE LOWER(a.group.name) = LOWER(:groupName) ")
    List<Animal> findByGroupNameIgnoreCase(String groupName);

}
