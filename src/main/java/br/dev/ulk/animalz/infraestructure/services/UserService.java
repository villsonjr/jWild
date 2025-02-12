package br.dev.ulk.animalz.infraestructure.services;

import br.dev.ulk.animalz.application.exceptions.AuthenticationFailedException;
import br.dev.ulk.animalz.application.v1.payloads.UserRequestDTO;
import br.dev.ulk.animalz.application.v1.payloads.dtos.UserDTO;
import br.dev.ulk.animalz.domain.models.User;
import br.dev.ulk.animalz.infraestructure.repositories.AbstractRepository;
import br.dev.ulk.animalz.infraestructure.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService extends AbstractService<User, Long> {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected AbstractRepository<User, Long> getRepository() {
        return userRepository;
    }

    public UserDTO save(User user) {
        return UserDTO.fromEntity(create(user));
    }

    @Transactional
    public Boolean delete(Long id) {
        Optional<User> user = findByID(id);
        if (user.isPresent()) {
            delete(user.get());
            return true;
        }
        return false;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = findAll();
        return users.stream()
                .map(UserDTO::fromEntity)
                .toList();
    }

    public boolean isRegistered(UserRequestDTO dto) {
        return userRepository.findByUsernameOrEmail(dto.getUsername(), dto.getEmail()).isPresent();
    }

    public User loadUserByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationFailedException("User not found - SERVICE"));
    }
}