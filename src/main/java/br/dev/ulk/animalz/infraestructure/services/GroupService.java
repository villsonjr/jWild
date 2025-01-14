package br.dev.ulk.animalz.infraestructure.services;

import br.dev.ulk.animalz.application.controllers.GroupController;
import br.dev.ulk.animalz.application.dtos.GroupDTO;
import br.dev.ulk.animalz.application.exceptions.ResourceNotFoundException;
import br.dev.ulk.animalz.domain.models.Group;
import br.dev.ulk.animalz.infraestructure.repositories.GroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Optional<Group> findById(Long id) {
        return groupRepository.findById(id);
    }

    public Group save(Group animal) {
        return groupRepository.save(animal);
    }

    @Transactional
    public void delete(Long id) {
        groupRepository.deleteById(id);
    }

    public List<GroupDTO> getAllGroups() {
        List<Group> groups = findAll();
        return groups.stream()
                .map(group -> addLink(group, GroupDTO.fromEntity(group)))
                .collect(Collectors.toList());
    }

    public GroupDTO getGroupById(Long id) {
        Group group = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id " + id));
        return addLinks(group, GroupDTO.fromEntity(group));
    }

    @Transactional
    public GroupDTO createGroup(GroupDTO groupDTO) {
        Group group = new Group();
        group.setName(groupDTO.getName());
        return addLinks(group, GroupDTO.fromEntity(save(group)));
    }

    @Transactional
    public GroupDTO updateGroup(Long id, GroupDTO groupDTO) {
        Group group = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id " + id));

        group.setName(groupDTO.getName());

        return addLinks(group, GroupDTO.fromEntity(save(group)));
    }

    private GroupDTO addLink(Group group, GroupDTO groupDTO) {
        Link selfLink = linkTo(WebMvcLinkBuilder.methodOn(GroupController.class).getGroupById(group.getId())).withSelfRel();
        groupDTO.add(selfLink);

        return groupDTO;
    }

    private GroupDTO addLinks(Group group, GroupDTO groupDTO) {
        Link selfLink = linkTo(WebMvcLinkBuilder.methodOn(GroupController.class).getGroupById(group.getId())).withSelfRel();
        groupDTO.add(selfLink);

        Link groupLink = linkTo(WebMvcLinkBuilder.methodOn(GroupController.class).getAllGroups()).withRel("groups");
        groupDTO.add(groupLink);

        return groupDTO;
    }
}
