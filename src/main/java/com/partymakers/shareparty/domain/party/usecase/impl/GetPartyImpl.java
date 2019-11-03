package com.partymakers.shareparty.domain.party.usecase.impl;

import com.partymakers.shareparty.domain.party.entity.PartyRoom;
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;
import com.partymakers.shareparty.domain.party.usecase.GetParty;
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetPartyImpl implements GetParty {

    private final PartyRoomRepository repository;

    @Override
    public PartyRoom getParty(Long partyId) {
        return repository.findById(partyId).orElseThrow(NotFoundException::new);
    }
}
