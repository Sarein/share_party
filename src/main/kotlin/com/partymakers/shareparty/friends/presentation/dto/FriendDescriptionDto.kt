package com.partymakers.shareparty.friends.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class FriendDescriptionDto(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("nickName")
    val nickName: String,
    @JsonProperty("mail")
    val mail: String
)