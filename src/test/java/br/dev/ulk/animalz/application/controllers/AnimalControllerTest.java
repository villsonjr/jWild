package br.dev.ulk.animalz.application.controllers;

import br.dev.ulk.animalz.application.dtos.AnimalDTO;
import br.dev.ulk.animalz.application.dtos.GroupDTO;
import br.dev.ulk.animalz.infraestructure.services.AnimalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AnimalController.class)
class AnimalControllerTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;

    private AnimalDTO createAnimalDTO(Long id, String scientificName, String specie, Double size, Double mass, String status, GroupDTO group) {
        return AnimalDTO.builder()
                .id(id)
                .scientificName(scientificName)
                .specie(specie)
                .size(size)
                .mass(mass)
                .status(status)
                .group(group)
                .build();
    }

    private GroupDTO createGroupDTO(Long id, String name) {
        return GroupDTO.builder()
                .id(id)
                .name(name)
                .build();
    }

    @Test
    void testGetAllAnimals() throws Exception {
        GroupDTO mammalGroup = createGroupDTO(1L, "Mammals");

        List<AnimalDTO> animals = List.of(
                createAnimalDTO(1L, "Panthera leo", "Lion", 1.8, 190.5, "ACTIVE", mammalGroup),
                createAnimalDTO(2L, "Elephas maximus", "Elephant", 3.0, 5000.0, "ACTIVE", mammalGroup)
        );

        when(animalService.getAllAnimals()).thenReturn(animals);

        mockMvc.perform(get("/api/animals"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].scientificName").value("Panthera leo"))
                .andExpect(jsonPath("$[0].specie").value("Lion"))
                .andExpect(jsonPath("$[0].size").value(1.8))
                .andExpect(jsonPath("$[0].mass").value(190.5))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"))
                .andExpect(jsonPath("$[0].group.id").value(1))
                .andExpect(jsonPath("$[0].group.name").value("Mammals"));
    }

    @Test
    void testGetAnimalById() throws Exception {
        GroupDTO mammalGroup = createGroupDTO(1L, "Mammals");
        AnimalDTO animal = createAnimalDTO(1L, "Panthera leo", "Lion", 1.8, 190.5, "ACTIVE", mammalGroup);

        when(animalService.getAnimalById(1L)).thenReturn(animal);

        mockMvc.perform(get("/api/animals/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.scientificName").value("Panthera leo"))
                .andExpect(jsonPath("$.specie").value("Lion"))
                .andExpect(jsonPath("$.size").value(1.8))
                .andExpect(jsonPath("$.mass").value(190.5))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.group.id").value(1))
                .andExpect(jsonPath("$.group.name").value("Mammals"));
    }

    @Test
    void testCreateAnimal() throws Exception {
        GroupDTO mammalGroup = createGroupDTO(1L, "Mammals");
        AnimalDTO animal = createAnimalDTO(1L, "Panthera leo", "Lion", 1.8, 190.5, "ACTIVE", mammalGroup);

        when(animalService.createAnimal(any(AnimalDTO.class))).thenReturn(animal);

        mockMvc.perform(post("/api/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "scientificName": "Panthera leo",
                                    "specie": "Lion",
                                    "size": 1.8,
                                    "mass": 190.5,
                                    "status": "ACTIVE",
                                    "group": {
                                        "id": 1,
                                        "name": "Mammals"
                                    }
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.scientificName").value("Panthera leo"))
                .andExpect(jsonPath("$.specie").value("Lion"))
                .andExpect(jsonPath("$.size").value(1.8))
                .andExpect(jsonPath("$.mass").value(190.5))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.group.id").value(1))
                .andExpect(jsonPath("$.group.name").value("Mammals"));
    }

    @Test
    void testUpdateAnimal() throws Exception {
        GroupDTO mammalGroup = createGroupDTO(1L, "Mammals");
        AnimalDTO animal = createAnimalDTO(1L, "Panthera leo", "Lion", 1.8, 190.5, "ACTIVE", mammalGroup);

        when(animalService.updateAnimal(eq(1L), any(AnimalDTO.class))).thenReturn(animal);

        mockMvc.perform(put("/api/animals/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "scientificName": "Panthera leo",
                                    "specie": "Lion",
                                    "size": 1.8,
                                    "mass": 190.5,
                                    "status": "ACTIVE",
                                    "group": {
                                        "id": 1,
                                        "name": "Mammals"
                                    }
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.scientificName").value("Panthera leo"))
                .andExpect(jsonPath("$.specie").value("Lion"))
                .andExpect(jsonPath("$.size").value(1.8))
                .andExpect(jsonPath("$.mass").value(190.5))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.group.id").value(1))
                .andExpect(jsonPath("$.group.name").value("Mammals"));
    }

    @Test
    void testDeleteAnimal() throws Exception {
        doNothing().when(animalService).delete(1L);

        mockMvc.perform(delete("/api/animals/1"))
                .andExpect(status().isNoContent());
    }
}