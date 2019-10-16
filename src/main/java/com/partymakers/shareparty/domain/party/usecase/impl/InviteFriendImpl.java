package com.partymakers.shareparty.domain.usecases.party.impl;

import com.partymakers.shareparty.domain.party.usecase.InviteFriend;
import com.partymakers.shareparty.domain.usecases.party.port.InvitedFriendsRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InviteFriendImpl implements InviteFriend {

    private final InvitedFriendsRepository repository;

    @Override
    public void inviteFriend(String nickName, long partyId) {
        repository.inviteFriend(nickName, partyId);
    }
}
