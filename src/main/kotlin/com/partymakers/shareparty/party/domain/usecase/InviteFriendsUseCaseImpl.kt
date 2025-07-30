package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import org.springframework.stereotype.Service

@Service
internal class InviteFriendsUseCaseImpl(
    private val partyRoomRepository: PartyRoomRepository,
) : InviteFriendsUseCase {

    override fun invoke(roomId: Long, nickNames: List<String>): PartyRoom =
        partyRoomRepository.addFriends(roomId, nickNames)
            ?: throw NotFoundException("Party room or friend does not exist")
} 