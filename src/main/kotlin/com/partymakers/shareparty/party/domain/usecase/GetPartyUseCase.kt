package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoom

internal interface GetPartyUseCase {
    operator fun invoke(partyId: Long): PartyRoom?
} 