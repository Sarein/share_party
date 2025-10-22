package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import org.springframework.stereotype.Service

@Service
internal class CreatePartyRoomUseCaseImpl(
    private val repository: PartyRoomRepository
) : CreatePartyRoomUseCase {

    override fun invoke(partyName: String): Long =
        repository.create(partyName)
}