package br.dev.ulk.animalz.infraestructure.repositories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface AbstractRepository<T, ID> extends JpaRepository<T, ID> {

    Optional<T> findOne(Specification<T> specification);

}
