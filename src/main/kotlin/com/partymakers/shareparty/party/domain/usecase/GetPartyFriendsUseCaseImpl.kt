package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.friends.domain.entity.Friend
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class GetPartyFriendsUseCaseImpl(
    private val repository: PartyRoomRepository
) : GetPartyFriendsUseCase {

    override fun getPartyFriends(partyId: Long): Set<Friend> =
        repository.findById(partyId)
            .map { it.friends }
            .orElseThrow { NotFoundException("Party room with id: $partyId not found") }
} 