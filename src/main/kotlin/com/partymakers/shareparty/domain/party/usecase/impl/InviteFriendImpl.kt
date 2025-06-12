package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.domain.friends.port.FriendsRepository
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository
import com.partymakers.shareparty.domain.party.usecase.InviteFriend
import com.partymakers.shareparty.domain.party.usecase.exception.AlreadyExistException
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException

class InviteFriendImpl(
    private val partyRoomRepository: PartyRoomRepository,
    private val friendsRepository: FriendsRepository,
) : InviteFriend {

    override fun inviteFriend(nickName: String, partyId: Long) {
        val friend = friendsRepository.findOneById(nickName)
            .orElseThrow { NotFoundException() }

        val room = partyRoomRepository.findById(partyId)
            .orElseThrow { NotFoundException() }

        if (room.friends.contains(friend)) {
            throw AlreadyExistException("Friend ${friend.nickName} already invited")
        }

        val changedRoom = room.copy(friends = room.friends + friend)
        partyRoomRepository.save(changedRoom)
    }
} 