package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.domain.friends.entity.Friend
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository
import com.partymakers.shareparty.domain.party.usecase.GetPartyFriends
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class GetPartyFriendsImpl(
    private val repository: PartyRoomRepository
) : GetPartyFriends {

    override fun getPartyFriends(partyId: Long): Set<Friend> =
        repository.findById(partyId)
            .map { it.friends }
            .orElseThrow { NotFoundException("Party room with id: $partyId not found") }
} 