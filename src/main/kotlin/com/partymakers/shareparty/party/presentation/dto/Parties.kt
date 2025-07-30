package com.partymakers.shareparty.party.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.partymakers.shareparty.party.domain.entity.PartyRoom

internal data class Parties(
    @JsonProperty("parties")
    val partyRoomDescriptions: List<PartyRoom>
) 