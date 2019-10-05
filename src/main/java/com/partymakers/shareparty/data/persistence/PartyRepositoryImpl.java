package com.partymakers.shareparty.data.persistence;

import com.partymakers.shareparty.data.persistence.mapping.PartyRoomMapping;
import com.partymakers.shareparty.domain.entity.PartyRoom;
import com.partymakers.shareparty.domain.usecases.port.PartyRoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Collectors;

public class PartyRepositoryImpl implements PartyRoomRepository {

    private JpaRepository<PartyRoomEntity, Long> repository;

    public PartyRepositoryImpl(JpaRepository<PartyRoomEntity, Long> repository) {
        this.repository = repository;
    }

    @Override
    public PartyRoom save(PartyRoom entity) {
        return PartyRoomMapping.transform(repository.save(new PartyRoomEntity(entity.getId(), entity.getName())));
    }

    @Override
    public <S extends PartyRoom> Iterable<S> saveAll(Iterable<S> entities) {
        entities.forEach(entity -> save(entity));
        return entities;
    }

    @Override
    public Optional<PartyRoom> findById(Long aLong) {
        return repository.findById(aLong).map(PartyRoomMapping::transform);
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<PartyRoom> findAll() {
         return repository.findAll()
            .stream()
            .map(PartyRoomMapping::transform)
            .collect(Collectors.toList());
    }

    @Override
    public Iterable<PartyRoom> findAllById(Iterable<Long> longs) {
        return repository.findAllById(longs)
            .stream()
            .map(PartyRoomMapping::transform)
            .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }

    @Override
    public void delete(PartyRoom entity) {
        repository.delete(new PartyRoomEntity(entity.getId(), entity.getName()));
    }

    @Override
    public void deleteAll(Iterable<? extends PartyRoom> entities) {
        entities.forEach(entity -> delete(entity));
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
