package com.partymakers.shareparty.domain.usecases.party.impl;

import com.partymakers.shareparty.domain.usecases.party.KickFiend;
import com.partymakers.shareparty.domain.usecases.party.port.InvitedFriendsRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KickFiendImpl implements KickFiend {

    private final InvitedFriendsRepository repository;

    @Override
    public void kickFriend(String nickName, long partyId) {
        repository.deleteFriend(nickName, partyId);
    }
}
