package com.partymakers.shareparty.domain.party.usecase.impl;

import com.partymakers.shareparty.domain.party.entity.PartyRoom;
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;
import com.partymakers.shareparty.domain.party.usecase.KickFiend;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KickFiendImpl implements KickFiend {

    private final PartyRoomRepository partyRoomRepository;

    @Override
    public void kickFriend(String nickName, long partyId) {
        PartyRoom room = partyRoomRepository.findById(partyId);
        //TODO: add checking that`s friend not null or friendlist empty

        room.getFriends().removeIf(friend -> friend.getNickName().equalsIgnoreCase(nickName));
        partyRoomRepository.save(room);
    }
}
