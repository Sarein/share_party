package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import org.springframework.stereotype.Service

@Service
internal class GetPartiesUseCaseImpl(
    private val repository: PartyRoomRepository
) : GetPartiesUseCase {

    override fun invoke(): List<PartyRoom> =
        repository.findAll()
} 