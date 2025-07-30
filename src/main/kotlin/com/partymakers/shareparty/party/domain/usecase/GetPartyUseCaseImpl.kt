package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import org.springframework.stereotype.Service

@Service
internal class GetPartyUseCaseImpl(
    private val repository: PartyRoomRepository
) : GetPartyUseCase {

    override fun invoke(partyId: Long): PartyRoom? =
        repository.findById(partyId)
}