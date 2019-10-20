package com.partymakers.shareparty.domain.party.usecase.impl;

import com.partymakers.shareparty.domain.party.entity.PartyRoom;
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;
import com.partymakers.shareparty.domain.party.usecase.CreatePartyRoom;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreatePartyRoomImpl implements CreatePartyRoom {

    private final PartyRoomRepository repository;

    @Override
    public long createPartyRoom(String description) {
           return repository.save(new PartyRoom(description)).getDescription().getId();
    }
}
