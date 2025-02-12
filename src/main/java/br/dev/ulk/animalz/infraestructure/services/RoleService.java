package br.dev.ulk.animalz.infraestructure.services;

import br.dev.ulk.animalz.application.exceptions.ResourceNotFoundException;
import br.dev.ulk.animalz.domain.enumerations.RoleEnum;
import br.dev.ulk.animalz.domain.models.Role;
import br.dev.ulk.animalz.infraestructure.repositories.AbstractRepository;
import br.dev.ulk.animalz.infraestructure.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends AbstractService<Role, Long> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected AbstractRepository<Role, Long> getRepository() {
        return roleRepository;
    }

    public Role findByRole(RoleEnum role) {
        return roleRepository.findByRole(role)
                .orElseThrow(() -> new ResourceNotFoundException("Role Not Found"));
    }
}