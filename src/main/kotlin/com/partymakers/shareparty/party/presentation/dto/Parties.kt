package com.partymakers.shareparty.party.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.partymakers.shareparty.party.domain.entity.PartyRoomDescription

data class Parties(
    @JsonProperty("parties")
    val partyRoomDescriptions: Set<PartyRoomDescription>
) 