package com.partymakers.shareparty.domain.usecases.party.impl;

import com.partymakers.shareparty.domain.entity.PartyRoom;
import com.partymakers.shareparty.domain.usecases.party.CreatePartyRoom;

import org.springframework.data.repository.CrudRepository;

public class CreatePartyRoomImpl implements CreatePartyRoom {

    private CrudRepository<PartyRoom, Long>  repository;

    public CreatePartyRoomImpl(CrudRepository<PartyRoom, Long> repository) {
        this.repository = repository;
    }

    @Override
    public long createPartyRoom(PartyRoom room) {
           return repository.save(room).getId();
    }
}
