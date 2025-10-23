package com.partymakers.shareparty.party.data.dto

import com.fasterxml.jackson.annotation.JsonProperty

internal data class FriendEntity(
    @JsonProperty("nick_name")
    val nickName: String,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("e_mail")
    var mail: String? = null,
)

