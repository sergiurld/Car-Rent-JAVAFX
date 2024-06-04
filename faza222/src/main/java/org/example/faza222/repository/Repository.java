package org.example.faza222.repository;


import org.example.faza222.domain.Entity;

import java.util.Optional;

public interface Repository<ID, E extends Entity<ID>> {
    Optional<E> findOne(ID id);
    Iterable<E> findAll();
    Optional<E> save(E entity);
    Optional<E> delete(E entity);
    Optional<E> update(E entity);
}