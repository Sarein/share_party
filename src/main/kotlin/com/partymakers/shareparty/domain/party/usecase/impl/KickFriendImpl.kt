package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.domain.party.port.PartyRoomRepository
import com.partymakers.shareparty.domain.party.usecase.KickFriend
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException

class KickFriendImpl(
    private val partyRoomRepository: PartyRoomRepository
) : KickFriend {

    override fun kickFriend(nickName: String, partyId: Long) {
        val room = partyRoomRepository.findById(partyId)
            .orElseThrow { NotFoundException("Party room with id: $partyId not found") }

        val updatedRoom = room.copy(
            friends = room.friends.filterNot { it.nickName.equals(nickName, ignoreCase = true) }.toSet()
        )
        partyRoomRepository.save(updatedRoom)
    }
} 