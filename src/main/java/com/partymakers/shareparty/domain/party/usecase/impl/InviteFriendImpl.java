package com.partymakers.shareparty.domain.party.usecase.impl;

import com.partymakers.shareparty.domain.friends.port.FriendsRepository;
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;
import com.partymakers.shareparty.domain.party.usecase.InviteFriend;
import com.partymakers.shareparty.domain.party.usecase.exception.AlreadyExistException;
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InviteFriendImpl implements InviteFriend {

    private final PartyRoomRepository partyRoomRepository;
    private final FriendsRepository   friendsRepository;

    @Override
    public void inviteFriend(String nickName, long partyId) {
        friendsRepository.findOneById(nickName).ifPresentOrElse(friend ->{
                partyRoomRepository.findById(partyId).ifPresentOrElse(room -> {

                        if(room.getFriends().contains(friend)){
                            throw new AlreadyExistException("Friend "  + friend.getName() + " already invited");
                        }

                        room.getFriends().add(friend);

                        partyRoomRepository.save(room);},
                    () -> {throw new NotFoundException();});
            },
        () -> {throw new NotFoundException();});
    }
}
