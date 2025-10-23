package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.entity.PartyRoom
import org.springframework.stereotype.Service

@Service
internal class InviteFriendUseCaseImpl(
    private val repository: PartyRoomRepository,
) : InviteFriendUseCase {

    override fun invoke(nickName: String, roomId: Long): PartyRoom {
        if(!repository.existsById(roomId)) {
            throw NotFoundException("Requested room is not exist")
        }

        repository.addFriend(roomId, nickName)

        return repository.findById(roomId) ?: throw NotFoundException("Requested room is not exist")
    }
}