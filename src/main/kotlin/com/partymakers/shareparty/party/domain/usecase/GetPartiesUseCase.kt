package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoomDescription

interface GetPartiesUseCase {
    fun getParties(): Set<PartyRoomDescription>
} 