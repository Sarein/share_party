package com.partymakers.shareparty.friends.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class FriendDescription(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("nickName")
    val nickName: String,
    @JsonProperty("eMail")
    val eMail: String
)