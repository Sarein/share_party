package com.partymakers.shareparty.domain.party.usecase.impl;

import com.partymakers.shareparty.domain.party.entity.PartyRoomDescription;
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;
import com.partymakers.shareparty.domain.party.usecase.GetPartiesList;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetPartiesListImpl implements GetPartiesList {

    private final PartyRoomRepository repository;

    @Override
    public Set<PartyRoomDescription> getPartyList() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
            .map(partyRoom -> partyRoom.getDescription())
            .collect(Collectors.toSet());
    }
}
