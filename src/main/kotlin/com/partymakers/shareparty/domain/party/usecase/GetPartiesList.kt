package com.partymakers.shareparty.domain.party.usecase

import com.partymakers.shareparty.domain.party.entity.PartyRoomDescription

interface GetPartiesList {
    fun getPartyList(): Set<PartyRoomDescription>
} 