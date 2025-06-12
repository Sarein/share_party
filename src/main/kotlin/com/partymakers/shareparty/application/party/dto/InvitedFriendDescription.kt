package com.partymakers.shareparty.application.party.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class InvitedFriendDescription(
    @JsonProperty("nickName")
    val nickName: String
) 