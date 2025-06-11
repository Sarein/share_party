package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.domain.party.entity.PartyRoomDescription
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository
import com.partymakers.shareparty.domain.party.usecase.GetPartiesList

class GetPartiesListImpl(
    private val repository: PartyRoomRepository
) : GetPartiesList {

    override fun getPartyList(): Set<PartyRoomDescription> =
        repository.findAll().map { it.description }.toSet()
} 