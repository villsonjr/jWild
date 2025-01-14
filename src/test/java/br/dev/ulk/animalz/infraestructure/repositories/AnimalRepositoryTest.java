package br.dev.ulk.animalz.infraestructure.repositories;

import br.dev.ulk.animalz.domain.enumerations.StatusEnum;
import br.dev.ulk.animalz.domain.models.Animal;
import br.dev.ulk.animalz.domain.models.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.AssertionErrors;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;

    @MockBean
    private AnimalRepository mockAnimalRepository;

    @BeforeEach
    public void setUp() {
        Animal mockAnimal1 = new Animal(1L, "Panthera leo", "Lion", 1.8, 190.5, StatusEnum.ACTIVE, Group.builder().id(1L).name("Mammals").build());
        Animal mockAnimal2 = new Animal(2L, "Elephas maximus", "Elephant", 3.0, 5000.0, StatusEnum.ACTIVE, Group.builder().id(1L).name("Mammals").build());

        when(mockAnimalRepository.findByGroupNameIgnoreCase("mammals"))
                .thenReturn(List.of(mockAnimal1, mockAnimal2));
    }

    @Test
    void findByGroupNameIgnoreCase() {
        List<Animal> animals = animalRepository.findByGroupNameIgnoreCase("mammals");

        AssertionErrors.assertTrue("A lista de animais deve conter dois elementos", animals.size() == 2);
        AssertionErrors.assertTrue("O primeiro animal deve ser um leão", animals.get(0).getSpecie().equalsIgnoreCase("Lion"));
        AssertionErrors.assertTrue("O segundo animal deve ser um elefante", animals.get(1).getSpecie().equalsIgnoreCase("Elephant"));
    }
}