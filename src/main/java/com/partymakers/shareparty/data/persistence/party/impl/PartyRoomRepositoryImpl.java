package com.partymakers.shareparty.data.persistence.party.impl;

import com.partymakers.shareparty.data.persistence.party.PartyRoomPersistanceRepository;
import com.partymakers.shareparty.data.persistence.party.entity.PartyRoomEntity;
import com.partymakers.shareparty.domain.party.entity.PartyRoom;
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PartyRoomRepositoryImpl implements PartyRoomRepository {

    private final PartyRoomPersistanceRepository repository;

    @Override
    public PartyRoom save(PartyRoom partyRoom) {
        return repository.save(PartyRoomEntity.toPersistence(partyRoom)).toDomain();
    }

    @Override
    public PartyRoom findById(long id) {
        //TODO: AHTUNG! NPE possibly
        return repository.findById(id).get().toDomain();
    }

    @Override
    public Iterable<PartyRoom> findAll() {
        return repository.findAll().stream().map(partyRoomEntity -> partyRoomEntity.toDomain()).collect(Collectors.toList());
    }
}
