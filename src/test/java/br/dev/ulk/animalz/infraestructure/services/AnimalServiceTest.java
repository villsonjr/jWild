package br.dev.ulk.animalz.infraestructure.services;

import br.dev.ulk.animalz.application.dtos.AnimalDTO;
import br.dev.ulk.animalz.application.dtos.GroupDTO;
import br.dev.ulk.animalz.application.exceptions.ResourceNotFoundException;
import br.dev.ulk.animalz.domain.enumerations.StatusEnum;
import br.dev.ulk.animalz.domain.models.Animal;
import br.dev.ulk.animalz.domain.models.Group;
import br.dev.ulk.animalz.infraestructure.repositories.AnimalRepository;
import br.dev.ulk.animalz.infraestructure.repositories.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @InjectMocks
    private AnimalService animalService;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private GroupRepository groupRepository;

    private Animal animal;
    private AnimalDTO animalDTO;
    private Group group;

    @BeforeEach
    void setUp() {
        group = new Group(1L, "Mammals", new HashSet<>());
        animal = Animal.builder()
                .id(1L)
                .scientificName("Panthera leo")
                .specie("Lion")
                .size(1.8)
                .mass(190.5)
                .status(StatusEnum.ACTIVE)
                .group(group)
                .build();
        animalDTO = AnimalDTO.builder()
                .id(1L)
                .scientificName("Panthera leo")
                .specie("Lion")
                .size(1.8)
                .mass(190.5)
                .status("ACTIVE")
                .group(GroupDTO.builder().id(1L).name("Mammals").build())
                .build();
    }

    @Test
    void findAll_ShouldReturnListOfAnimals() {
        when(animalRepository.findAll()).thenReturn(List.of(animal));

        List<Animal> result = animalService.findAll();

        assertEquals(1, result.size());
        assertEquals("Panthera leo", result.get(0).getScientificName());
        verify(animalRepository).findAll();
    }

    @Test
    void findById_ShouldReturnAnimal_WhenIdExists() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        Optional<Animal> result = animalService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Panthera leo", result.get().getScientificName());
        verify(animalRepository).findById(1L);
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenIdDoesNotExist() {
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Animal> result = animalService.findById(1L);

        assertTrue(result.isEmpty());
        verify(animalRepository).findById(1L);
    }

    @Test
    void save_ShouldSaveAndReturnAnimal() {
        when(animalRepository.save(animal)).thenReturn(animal);

        Animal result = animalService.save(animal);

        assertNotNull(result);
        assertEquals("Panthera leo", result.getScientificName());
        verify(animalRepository).save(animal);
    }

    @Test
    void delete_ShouldDeleteAnimalById() {
        doNothing().when(animalRepository).deleteById(1L);

        animalService.delete(1L);

        verify(animalRepository).deleteById(1L);
    }

    @Test
    void getAllAnimals_ShouldReturnListOfAnimalDTOs() {
        when(animalRepository.findAll()).thenReturn(List.of(animal));

        List<AnimalDTO> result = animalService.getAllAnimals();

        assertEquals(1, result.size());
        assertEquals("Panthera leo", result.get(0).getScientificName());
        verify(animalRepository).findAll();
    }

    @Test
    void getAnimalById_ShouldReturnAnimalDTO_WhenIdExists() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        AnimalDTO result = animalService.getAnimalById(1L);

        assertNotNull(result);
        assertEquals("Panthera leo", result.getScientificName());
        verify(animalRepository).findById(1L);
    }

    @Test
    void getAnimalById_ShouldThrowException_WhenIdDoesNotExist() {
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> animalService.getAnimalById(1L));
        verify(animalRepository).findById(1L);
    }

    @Test
    void createAnimal_ShouldSaveAndReturnAnimalDTO() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);

        AnimalDTO result = animalService.createAnimal(animalDTO);

        assertNotNull(result);
        assertEquals("Panthera leo", result.getScientificName());
        verify(groupRepository).findById(1L);
        verify(animalRepository).save(any(Animal.class));
    }

    @Test
    void createAnimal_ShouldThrowException_WhenGroupDoesNotExist() {
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> animalService.createAnimal(animalDTO));
        verify(groupRepository).findById(1L);
    }

    @Test
    void updateAnimal_ShouldUpdateAndReturnAnimalDTO_WhenIdExists() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(animalRepository.save(animal)).thenReturn(animal);

        AnimalDTO updatedDTO = AnimalDTO.builder()
                .id(1L)
                .scientificName("Panthera tigris")
                .specie("Tiger")
                .size(2.1)
                .mass(220.0)
                .status("ACTIVE")
                .group(GroupDTO.builder().id(1L).name("Mammals").build())
                .build();

        animal.setScientificName("Panthera tigris");
        animal.setSpecie("Tiger");

        AnimalDTO result = animalService.updateAnimal(1L, updatedDTO);

        assertNotNull(result);
        assertEquals("Panthera tigris", result.getScientificName());
        verify(animalRepository).findById(1L);
        verify(animalRepository).save(animal);
    }

    @Test
    void updateAnimal_ShouldThrowException_WhenIdDoesNotExist() {
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        AnimalDTO updatedDTO = AnimalDTO.builder()
                .id(1L)
                .scientificName("Panthera tigris")
                .specie("Tiger")
                .size(2.1)
                .mass(220.0)
                .status("ACTIVE")
                .group(GroupDTO.builder().id(1L).name("Mammals").build())
                .build();

        assertThrows(ResourceNotFoundException.class, () -> animalService.updateAnimal(1L, updatedDTO));
        verify(animalRepository).findById(1L);
    }
}