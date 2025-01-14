package br.dev.ulk.animalz.infraestructure.services;

import br.dev.ulk.animalz.application.controllers.AnimalController;
import br.dev.ulk.animalz.application.controllers.GroupController;
import br.dev.ulk.animalz.application.dtos.AnimalDTO;
import br.dev.ulk.animalz.application.exceptions.ResourceNotFoundException;
import br.dev.ulk.animalz.domain.enumerations.StatusEnum;
import br.dev.ulk.animalz.domain.models.Animal;
import br.dev.ulk.animalz.domain.models.Group;
import br.dev.ulk.animalz.infraestructure.repositories.AnimalRepository;
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
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private GroupRepository groupRepository;

    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    public Optional<Animal> findById(Long id) {
        return animalRepository.findById(id);
    }

    public Animal save(Animal animal) {
        return animalRepository.save(animal);
    }

    @Transactional
    public void delete(Long id) {
        animalRepository.deleteById(id);
    }

    public List<AnimalDTO> getAllAnimals() {
        List<Animal> animals = findAll();
        return animals.stream()
                .map(animal -> addLinksSelf(animal, AnimalDTO.fromEntity(animal)))
                .toList();
    }

    public AnimalDTO getAnimalById(Long id) {
        Animal animal = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with id " + id));
        return addLinks(animal, AnimalDTO.fromEntity(animal));
    }

    public List<AnimalDTO> getAnimalsByGroup(String group) {
        Optional<Object> groupParameter = parseGroupParameter(group);

        return parseGroupParameter(group).get() instanceof Long ?
                getAnimalsByGroupId((Long) groupParameter.get()) :
                getAnimalsByGroupName((String) groupParameter.get());

    }

    @Transactional
    public AnimalDTO createAnimal(AnimalDTO animalDTO) {
        Group group = groupRepository.findById(animalDTO.getGroup().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id " + animalDTO.getGroup().getId()));

        Animal animal = Animal.builder()
                .scientificName(animalDTO.getScientificName())
                .specie(animalDTO.getSpecie())
                .size(animalDTO.getSize())
                .mass(animalDTO.getMass())
                .group(group)
                .status(StatusEnum.ACTIVE)
                .build();

        return addLinks(animal, AnimalDTO.fromEntity(save(animal)));
    }

    @Transactional
    public AnimalDTO updateAnimal(Long id, AnimalDTO animalDTO) {
        Animal animal = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with id " + id));

        animal.setScientificName(animalDTO.getScientificName());
        animal.setSpecie(animalDTO.getSpecie());
        animal.setSize(animalDTO.getSize());
        animal.setMass(animalDTO.getMass());

        return addLinks(animal, AnimalDTO.fromEntity(save(animal)));
    }

    @Transactional
    public AnimalDTO partialUpdateAnimal(Long id, AnimalDTO animalDTO) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with id " + id));

        animal.setScientificName(animalDTO.getScientificName());
        animal.setSpecie(animalDTO.getSpecie());

        return addLinks(animal, AnimalDTO.fromEntity(save(animal)));
    }

    private Optional<Object> parseGroupParameter(String group) {
        try {
            return Optional.of(Long.parseLong(group));
        } catch (NumberFormatException e) {
            return Optional.of(group);
        }
    }

    private List<AnimalDTO> getAnimalsByGroupId(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id " + groupId));

        List<Animal> animals = animalRepository.findAnimalsByGroupId(group.getId());
        return animals.stream()
                .map(animal -> addLinksSelf(animal, AnimalDTO.fromEntity(animal)))
                .toList();
    }

    private List<AnimalDTO> getAnimalsByGroupName(String groupName) {
        Group group = groupRepository.findByNameIgnoreCase(groupName)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with name " + groupName));

        List<Animal> animals = animalRepository.findByGroupNameIgnoreCase(group.getName());
        return animals.stream()
                .map(animal -> addLinksSelf(animal, AnimalDTO.fromEntity(animal)))
                .toList();
    }

    private AnimalDTO addLinks(Animal animal, AnimalDTO animalDTO) {
        Link allLink = linkTo(WebMvcLinkBuilder.methodOn(AnimalController.class).getAllAnimals()).withRel("animals");
        animalDTO.add(allLink);

        addGroupLink(animal, animalDTO);

        return animalDTO;
    }

    private AnimalDTO addLinksSelf(Animal animal, AnimalDTO animalDTO) {
        Link selfLink = linkTo(WebMvcLinkBuilder.methodOn(AnimalController.class).getAnimalById(animal.getId())).withSelfRel();
        animalDTO.add(selfLink);

        addGroupLink(animal, animalDTO);

        return animalDTO;
    }

    private void addGroupLink(Animal animal, AnimalDTO animalDTO) {
        Link groupLink = linkTo(WebMvcLinkBuilder.methodOn(GroupController.class)
                .getGroupById(animal.getGroup().getId()))
                .withRel("group");
        animalDTO.getGroup().add(groupLink);
    }
}
