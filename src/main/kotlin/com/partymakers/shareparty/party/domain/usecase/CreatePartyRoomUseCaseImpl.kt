package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import org.springframework.stereotype.Service

@Service
class CreatePartyRoomUseCaseImpl(
    private val repository: PartyRoomRepository
) : CreatePartyRoomUseCase {

    override fun createPartyRoom(description: String): Long =
        repository.save(PartyRoom(description)).description.id
} 