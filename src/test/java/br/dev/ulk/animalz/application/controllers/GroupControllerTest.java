package br.dev.ulk.animalz.application.controllers;

import br.dev.ulk.animalz.application.dtos.GroupDTO;
import br.dev.ulk.animalz.infraestructure.services.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GroupControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private GroupController groupController;

    @Mock
    private GroupService groupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
    }

    @Test
    void getAllGroups_shouldReturnOkWithGroups() throws Exception {
        GroupDTO groupDTO = new GroupDTO(1L, "Mammals");
        when(groupService.getAllGroups()).thenReturn(List.of(groupDTO));

        mockMvc.perform(get("/api/groups")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Mammals"));

        verify(groupService, times(1)).getAllGroups();
    }

    @Test
    void getAllGroups_shouldReturnNoContent() throws Exception {
        when(groupService.getAllGroups()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/groups")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(groupService, times(1)).getAllGroups();
    }

    @Test
    void getGroupById_shouldReturnOkWithGroup() throws Exception {
        GroupDTO groupDTO = new GroupDTO(1L, "Mammals");
        when(groupService.getGroupById(1L)).thenReturn(groupDTO);

        mockMvc.perform(get("/api/groups/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Mammals"));

        verify(groupService, times(1)).getGroupById(1L);
    }

    @Test
    void createGroup_shouldReturnCreatedWithGroup() throws Exception {
        GroupDTO groupDTO = new GroupDTO(1L, "Mammals");
        when(groupService.createGroup(any(GroupDTO.class))).thenReturn(groupDTO);

        mockMvc.perform(post("/api/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Mammals\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Mammals"));

        verify(groupService, times(1)).createGroup(any(GroupDTO.class));
    }

    @Test
    void updateGroup_shouldReturnOkWithUpdatedGroup() throws Exception {
        GroupDTO groupDTO = new GroupDTO(1L, "Birds");
        when(groupService.updateGroup(eq(1L), any(GroupDTO.class))).thenReturn(groupDTO);

        mockMvc.perform(put("/api/groups/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Birds\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Birds"));

        verify(groupService, times(1)).updateGroup(eq(1L), any(GroupDTO.class));
    }

    @Test
    void deleteGroup_shouldReturnNoContent() throws Exception {
        doNothing().when(groupService).delete(1L);

        mockMvc.perform(delete("/api/groups/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(groupService, times(1)).delete(1L);
    }
}