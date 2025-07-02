package com.partymakers.shareparty.party.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class InvitedFriendDescription(
    @JsonProperty("nickName")
    val nickName: String
) 