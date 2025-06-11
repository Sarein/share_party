package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.domain.party.entity.PartyRoom
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository
import com.partymakers.shareparty.domain.party.usecase.CreatePartyRoom

class CreatePartyRoomImpl(
    private val repository: PartyRoomRepository
) : CreatePartyRoom {

    override fun createPartyRoom(description: String): Long =
        repository.save(PartyRoom(description)).description.id
} 