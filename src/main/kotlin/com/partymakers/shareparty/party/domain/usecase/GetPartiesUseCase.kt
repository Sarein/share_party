package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoom

internal interface GetPartiesUseCase {
    operator fun invoke(): List<PartyRoom>
} 