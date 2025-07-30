package com.partymakers.shareparty.party.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty

internal data class InvitedFriendDescription(
    @JsonProperty("nickName")
    val nickName: String
) 