package com.partymakers.shareparty.domain.party.usecase

import com.partymakers.shareparty.domain.party.entity.PartyRoom

interface GetParty {
    fun getParty(partyId: Long): PartyRoom
} 