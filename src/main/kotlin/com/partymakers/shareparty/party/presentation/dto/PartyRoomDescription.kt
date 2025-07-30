package com.partymakers.shareparty.party.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty

internal data class PartyRoomDescription(
    @JsonProperty("name")
    val name: String
) 