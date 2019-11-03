package com.partymakers.shareparty.domain.party.usecase.impl;

import com.partymakers.shareparty.domain.friends.entity.Friend;
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;
import com.partymakers.shareparty.domain.party.usecase.GetPartyFriends;
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException;

import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetPartyFriendsImpl implements GetPartyFriends {

    private final PartyRoomRepository repository;

    @Override
    public Set<Friend> getPartyFriends(long partyId) {
        return repository.findById(partyId).map(partyRoom -> partyRoom.getFriends())
            .orElseThrow(NotFoundException::new);
    }
}
