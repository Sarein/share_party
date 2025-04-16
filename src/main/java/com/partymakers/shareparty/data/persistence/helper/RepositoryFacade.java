package com.partymakers.shareparty.data.persistence.helper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.stream.Collectors;

public class RepositoryFacade <T, U, R> implements CrudRepository<T, R> {

    private JpaRepository<U, R>     repository;
    private DomainMapper<T, U>      domainMapper;
    private PersistenceMapper<U, T> persistenceMapper;

    public RepositoryFacade(JpaRepository<U, R> repository, DomainMapper<T, U> domainMapper, PersistenceMapper<U, T> persistenceMapper) {
        this.repository = repository;
        this.domainMapper = domainMapper;
        this.persistenceMapper = persistenceMapper;
    }

    @Override
    public <S extends T> S save(S entity) {
        return (S) persistenceMapper.toDomain(repository.save(domainMapper.toPersistence(entity)));
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {

        entities.forEach(entity -> save(entity));
        return entities;
    }

    @Override
    public Optional<T> findById(R r) {
        return repository.findById(r).map((persistence) -> persistenceMapper.toDomain(persistence));
    }

    @Override
    public boolean existsById(R r) {
        return repository.existsById(r);
    }

    @Override
    public Iterable<T> findAll() {
         return repository.findAll()
            .stream()
            .map( (persistence) -> persistenceMapper.toDomain(persistence))
            .collect(Collectors.toList());
    }

    @Override
    public Iterable<T> findAllById(Iterable<R> rs) {
        return repository.findAllById(rs)
            .stream()
            .map((persistence) -> persistenceMapper.toDomain(persistence))
            .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void deleteById(R r) {
        repository.deleteById(r);
    }

    @Override
    public void delete(T entity) {
        repository.delete(domainMapper.toPersistence(entity));
    }

    @Override
    public void deleteAllById(Iterable<? extends R> entities) {
        repository.deleteAllById(entities);
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(entity -> delete(entity));
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
