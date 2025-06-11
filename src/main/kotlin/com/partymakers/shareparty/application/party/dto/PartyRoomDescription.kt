package com.partymakers.shareparty.application.party.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PartyRoomDescription(
    @JsonProperty("name")
    val name: String
) 