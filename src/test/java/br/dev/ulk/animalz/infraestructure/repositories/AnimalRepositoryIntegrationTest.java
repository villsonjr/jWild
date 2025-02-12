package br.dev.ulk.animalz.infraestructure.repositories;

import br.dev.ulk.animalz.domain.enumerations.StatusEnum;
import br.dev.ulk.animalz.domain.models.Animal;
import br.dev.ulk.animalz.domain.models.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class AnimalRepositoryIntegrationTest {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private GroupRepository groupRepository;

    private Group group;
    private Animal animal;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        group = Group.builder()
                .name("Mammals")
                .build();
        group.setId(1L);
        group = groupRepository.save(group);
        animal = Animal.builder()
                .scientificName("Panthera leo")
                .specie("Lion")
                .size(1.8)
                .mass(190.0)
                .status(StatusEnum.ACTIVE)
                .group(group)
                .build();
        animal.setId(1L);
    }

    @Test
    @Transactional
    public void testSaveAnimalWithValidGroup() {
        Animal savedAnimal = animalRepository.save(animal);
        assertNotNull(savedAnimal);
        assertNotNull(savedAnimal.getId());
        assertEquals("Panthera leo", savedAnimal.getScientificName());
        assertEquals("Mammals", savedAnimal.getGroup().getName());
    }

    @Test
    public void testFindAnimalsByGroupId() {
        animalRepository.save(animal);
        List<Animal> result = animalRepository.findAnimalsByGroupId(group.getId());
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Panthera leo", result.get(0).getScientificName());
    }

    @Test
    @Transactional
    public void testFindByGroupNameIgnoreCase() {
        animalRepository.save(animal);
        List<Animal> result = animalRepository.findByGroupNameIgnoreCase("mammals");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Panthera leo", result.get(0).getScientificName());
    }
}