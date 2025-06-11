package com.partymakers.shareparty.application.party.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.partymakers.shareparty.domain.party.entity.PartyRoomDescription

data class Parties(
    @JsonProperty("parties")
    val partyRoomDescriptions: Set<PartyRoomDescription>
) 