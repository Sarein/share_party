package com.partymakers.shareparty.domain.party.usecase.impl;

import com.partymakers.shareparty.domain.party.entity.PartyRoomDescription;
import com.partymakers.shareparty.domain.party.usecase.GetPartiesList;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetPartiesListImpl implements GetPartiesList {

    final private CrudRepository<PartyRoomDescription, Long> repository;

    @Override
    public List<PartyRoomDescription> getPartyList() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }
}
