package com.partymakers.shareparty.data.persistence.party.impl;

import com.partymakers.shareparty.data.persistence.friends.entity.InvitedFriends;
import com.partymakers.shareparty.data.persistence.party.InvitedFriendsPersistenceRepository;
import com.partymakers.shareparty.domain.usecases.party.port.InvitedFriendsRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InvitedFriendsRepositoryImpl implements InvitedFriendsRepository {

    private final InvitedFriendsPersistenceRepository repository;

    @Override
    public void inviteFriend(String nickName, Long partyRoom) {
         repository.save(new InvitedFriends(nickName, partyRoom));
    }

    @Override
    public void deleteFriend(String nickName, Long partyRoom) {
        repository.delete(nickName, partyRoom);
    }
}
