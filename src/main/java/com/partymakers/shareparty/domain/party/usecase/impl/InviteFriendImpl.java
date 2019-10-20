package com.partymakers.shareparty.domain.party.usecase.impl;

import com.partymakers.shareparty.domain.friends.entity.Friend;
import com.partymakers.shareparty.domain.friends.port.FriendsRepository;
import com.partymakers.shareparty.domain.party.entity.PartyRoom;
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;
import com.partymakers.shareparty.domain.party.usecase.InviteFriend;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InviteFriendImpl implements InviteFriend {

    private final PartyRoomRepository partyRoomRepository;
    private final FriendsRepository   friendsRepository;

    @Override
    public void inviteFriend(String nickName, long partyId) {

        Friend friend = friendsRepository.findOneById(nickName);
        //TODO: add checking that`s friend not null

        PartyRoom room = partyRoomRepository.findById(partyId);
        //TODO: add checking that`s friend not null or friends already exist

        room.getFriends().add(friend);
        partyRoomRepository.save(room);
    }
}
