package com.partymakers.shareparty.domain.party.usecase.impl;

import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;
import com.partymakers.shareparty.domain.party.usecase.KickFiend;
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KickFiendImpl implements KickFiend {

    private final PartyRoomRepository partyRoomRepository;

    @Override
    public void kickFriend(String nickName, long partyId) {
        partyRoomRepository.findById(partyId).ifPresentOrElse(
            room -> {
                room.getFriends().removeIf(friend -> friend.getNickName().equalsIgnoreCase(nickName));
                partyRoomRepository.save(room);
            },
            () -> {throw new NotFoundException();});
    }
}
