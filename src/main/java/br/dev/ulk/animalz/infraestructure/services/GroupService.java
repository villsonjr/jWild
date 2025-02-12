package br.dev.ulk.animalz.infraestructure.services;

import br.dev.ulk.animalz.application.exceptions.ResourceNotFoundException;
import br.dev.ulk.animalz.application.v1.controllers.implementations.GroupControllerV1Impl;
import br.dev.ulk.animalz.application.v1.payloads.dtos.GroupDTO;
import br.dev.ulk.animalz.domain.models.Group;
import br.dev.ulk.animalz.infraestructure.repositories.AbstractRepository;
import br.dev.ulk.animalz.infraestructure.repositories.GroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class GroupService extends AbstractService<Group, Long> {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    protected AbstractRepository<Group, Long> getRepository() {
        return groupRepository;
    }

    public Group save(Group animal) {
        return create(animal);
    }

    @Transactional
    public Boolean delete(Long id) {
        Optional<Group> group = findByID(id);
        if (group.isPresent()) {
            delete(group.get());
            return true;
        }
        return false;
    }

    public List<GroupDTO> getAllGroups() {
        List<Group> groups = findAll();
        return groups.stream()
                .map(group -> addLink(group, GroupDTO.fromEntity(group)))
                .toList();
    }

    public GroupDTO getGroupById(Long id) {
        Group group = findByID(id)
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
        Group group = findByID(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id " + id));
        group.setName(groupDTO.getName());
        return addLinks(group, GroupDTO.fromEntity(save(group)));
    }

    private GroupDTO addLink(Group group, GroupDTO groupDTO) {
        Link selfLink = linkTo(WebMvcLinkBuilder.methodOn(GroupControllerV1Impl.class).getGroupById(group.getId())).withSelfRel();
        groupDTO.add(selfLink);
        return groupDTO;
    }

    private GroupDTO addLinks(Group group, GroupDTO groupDTO) {
        Link selfLink = linkTo(WebMvcLinkBuilder.methodOn(GroupControllerV1Impl.class).getGroupById(group.getId())).withSelfRel();
        groupDTO.add(selfLink);
        Link groupLink = linkTo(WebMvcLinkBuilder.methodOn(GroupControllerV1Impl.class).getAllGroups()).withRel("groups");
        groupDTO.add(groupLink);
        return groupDTO;
    }
}