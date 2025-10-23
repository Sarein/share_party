package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
internal class KickFriendUseCaseImpl(
    private val repository: PartyRoomRepository
) : KickFriendUseCase {

    override fun invoke(roomId: Long, nickName: String): PartyRoom {
        if(!repository.existsById(roomId)) {
            throw NotFoundException("Requested room is not exist")
        }

        repository.deleteFriend(roomId, nickName)

        return repository.findById(roomId) ?: throw NotFoundException("Requested room is not exist")
    }
}