package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoomDescription
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import org.springframework.stereotype.Service

@Service
class GetPartiesUseCaseImpl(
    private val repository: PartyRoomRepository
) : GetPartiesUseCase {

    override fun getParties(): Set<PartyRoomDescription> =
        repository.findAll().map { it.description }.toSet()
} 