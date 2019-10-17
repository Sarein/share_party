package com.partymakers.shareparty.domain.party.usecase.impl;

import com.partymakers.shareparty.domain.party.entity.PartyRoomDescription;
import com.partymakers.shareparty.domain.party.usecase.CreatePartyRoom;

import org.springframework.data.repository.CrudRepository;

public class CreatePartyRoomImpl implements CreatePartyRoom {

    private CrudRepository<PartyRoomDescription, Long> repository;

    public CreatePartyRoomImpl(CrudRepository<PartyRoomDescription, Long> repository) {
        this.repository = repository;
    }

    @Override
    public long createPartyRoom(PartyRoomDescription room) {
           return repository.save(room).getId();
    }
}
