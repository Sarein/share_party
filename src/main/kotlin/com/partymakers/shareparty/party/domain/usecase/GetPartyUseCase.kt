package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoom

interface GetPartyUseCase {
    fun getParty(partyId: Long): PartyRoom
} 