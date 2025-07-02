package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.domain.party.entity.PartyRoom
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository
import com.partymakers.shareparty.domain.party.usecase.GetParty
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class GetPartyImpl(
    private val repository: PartyRoomRepository
) : GetParty {

    override fun getParty(partyId: Long): PartyRoom =
        repository.findById(partyId).orElseThrow { NotFoundException() }
} 