package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
internal class KickFriendUseCaseImpl(
    private val partyRoomRepository: PartyRoomRepository
) : KickFriendUseCase {

    override fun invoke(roomId: Long, nickName: String): PartyRoom? =
        partyRoomRepository.deleteFriend(roomId, nickName)
            ?: throw NotFoundException("Party room or friend does not exist")
} 