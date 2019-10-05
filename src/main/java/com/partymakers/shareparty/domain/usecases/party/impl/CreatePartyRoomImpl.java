package com.partymakers.shareparty.domain.usecases.party.impl;

import com.partymakers.shareparty.domain.entity.PartyRoom;
import com.partymakers.shareparty.domain.usecases.party.CreatePartyRoom;
import com.partymakers.shareparty.domain.usecases.port.PartyRoomRepository;

public class CreatePartyRoomImpl implements CreatePartyRoom {

    private PartyRoomRepository repository;

    public CreatePartyRoomImpl(PartyRoomRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createPartyRoom(PartyRoom room) {
        repository.save(room);
    }
}
