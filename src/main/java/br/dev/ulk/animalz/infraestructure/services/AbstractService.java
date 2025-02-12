package br.dev.ulk.animalz.infraestructure.services;

import br.dev.ulk.animalz.infraestructure.repositories.AbstractRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T, ID> {

    protected abstract AbstractRepository<T, ID> getRepository();

    public T create(T entity) {
        return getRepository().save(entity);
    }

    public List<T> findAll() {
        return getRepository().findAll();
    }

    public Optional<T> findByID(ID id) {
        return getRepository().findById(id);
    }

    public void deleteByID(ID id) {
        getRepository().deleteById(id);
    }

    public void delete(T entity) {
        getRepository().delete(entity);
    }

}