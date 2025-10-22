package com.partymakers.shareparty.party.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty

internal data class InvitedFriendDescriptionDto(
    @JsonProperty("nickName")
    val nickName: String
) 