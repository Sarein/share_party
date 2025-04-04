package com.partymakers.shareparty.data.persistence.party.impl;

import com.partymakers.shareparty.data.persistence.party.PartyRoomPersistenceRepository;
import com.partymakers.shareparty.data.persistence.party.entity.PartyRoomEntity;
import com.partymakers.shareparty.domain.party.entity.PartyRoom;
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;

import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PartyRoomRepositoryImpl implements PartyRoomRepository {

    private final PartyRoomPersistenceRepository repository;

    @Override
    public PartyRoom save(PartyRoom partyRoom) {
        return repository.save(PartyRoomEntity.toPersistence(partyRoom)).toDomain();
    }

    @Override
    public Optional<PartyRoom> findById(long id) {
        return repository.findById(id).flatMap(partyRoom -> Optional.of(partyRoom.toDomain()));
    }

    @Override
    public Iterable<PartyRoom> findAll() {
        return repository.findAll().stream().map(partyRoomEntity -> partyRoomEntity.toDomain()).collect(Collectors.toList());
    }
}
