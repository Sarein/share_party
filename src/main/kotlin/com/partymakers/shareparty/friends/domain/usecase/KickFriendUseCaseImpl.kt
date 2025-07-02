package com.partymakers.shareparty.friends.domain.usecase

import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class KickFriendUseCaseImpl(
    private val partyRoomRepository: PartyRoomRepository
) : KickFriendUseCase {

    override fun kickFriend(nickName: String, partyId: Long) {
        val room = partyRoomRepository.findById(partyId)
            .orElseThrow { NotFoundException("Party room with id: $partyId not found") }

        val updatedRoom = room.copy(
            friends = room.friends.filterNot { it.nickName.equals(nickName, ignoreCase = true) }.toSet()
        )
        partyRoomRepository.save(updatedRoom)
    }
} 