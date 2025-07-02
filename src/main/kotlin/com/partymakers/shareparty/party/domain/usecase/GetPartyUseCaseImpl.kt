package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class GetPartyUseCaseImpl(
    private val repository: PartyRoomRepository
) : GetPartyUseCase {

    override fun getParty(partyId: Long): PartyRoom =
        repository.findById(partyId).orElseThrow { NotFoundException() }
} 