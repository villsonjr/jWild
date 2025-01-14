package br.dev.ulk.animalz.infraestructure.services;

import br.dev.ulk.animalz.application.dtos.GroupDTO;
import br.dev.ulk.animalz.application.exceptions.ResourceNotFoundException;
import br.dev.ulk.animalz.domain.models.Group;
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
class GroupServiceTest {

    @InjectMocks
    private GroupService groupService;

    @Mock
    private GroupRepository groupRepository;

    private Group group;
    private GroupDTO groupDTO;

    @BeforeEach
    void setUp() {
        group = new Group(1L, "Mammals", new HashSet<>());
        groupDTO = GroupDTO.builder().id(1L).name("Mammals").build();
    }

    @Test
    void findAll_ShouldReturnListOfGroups() {
        when(groupRepository.findAll()).thenReturn(List.of(group));

        List<Group> result = groupService.findAll();

        assertEquals(1, result.size());
        assertEquals("Mammals", result.get(0).getName());
        verify(groupRepository).findAll();
    }

    @Test
    void findById_ShouldReturnGroup_WhenIdExists() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        Optional<Group> result = groupService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Mammals", result.get().getName());
        verify(groupRepository).findById(1L);
    }

    @Test
    void findById_ShouldThrowException_WhenIdDoesNotExist() {
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Group> result = groupService.findById(1L);

        assertTrue(result.isEmpty());
        verify(groupRepository).findById(1L);
    }

    @Test
    void save_ShouldSaveAndReturnGroup() {
        when(groupRepository.save(group)).thenReturn(group);

        Group result = groupService.save(group);

        assertNotNull(result);
        assertEquals("Mammals", result.getName());
        verify(groupRepository).save(group);
    }

    @Test
    void delete_ShouldDeleteGroupById() {
        doNothing().when(groupRepository).deleteById(1L);

        groupService.delete(1L);

        verify(groupRepository).deleteById(1L);
    }

    @Test
    void getAllGroups_ShouldReturnListOfGroupDTOs() {
        when(groupRepository.findAll()).thenReturn(List.of(group));

        List<GroupDTO> result = groupService.getAllGroups();

        assertEquals(1, result.size());
        assertEquals("Mammals", result.get(0).getName());
        verify(groupRepository).findAll();
    }

    @Test
    void getGroupById_ShouldReturnGroupDTO_WhenIdExists() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        GroupDTO result = groupService.getGroupById(1L);

        assertNotNull(result);
        assertEquals("Mammals", result.getName());
        verify(groupRepository).findById(1L);
    }

    @Test
    void getGroupById_ShouldThrowException_WhenIdDoesNotExist() {
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> groupService.getGroupById(1L));
        verify(groupRepository).findById(1L);
    }

    @Test
    void createGroup_ShouldSaveAndReturnGroupDTO() {
        when(groupRepository.save(any(Group.class))).thenReturn(group);

        GroupDTO result = groupService.createGroup(groupDTO);

        assertNotNull(result);
        assertEquals("Mammals", result.getName());
        verify(groupRepository).save(any(Group.class));
    }

    @Test
    void updateGroup_ShouldUpdateAndReturnGroupDTO_WhenIdExists() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(groupRepository.save(group)).thenReturn(group);

        GroupDTO updatedDTO = GroupDTO.builder().id(1L).name("Updated Mammals").build();
        group.setName("Updated Mammals");

        GroupDTO result = groupService.updateGroup(1L, updatedDTO);

        assertNotNull(result);
        assertEquals("Updated Mammals", result.getName());
        verify(groupRepository).findById(1L);
        verify(groupRepository).save(group);
    }

    @Test
    void updateGroup_ShouldThrowException_WhenIdDoesNotExist() {
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        GroupDTO updatedDTO = GroupDTO.builder().id(1L).name("Updated Mammals").build();

        assertThrows(ResourceNotFoundException.class, () -> groupService.updateGroup(1L, updatedDTO));
        verify(groupRepository).findById(1L);
    }
}