package com.partymakers.shareparty.party.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PartyRoomDescription(
    @JsonProperty("name")
    val name: String
) 