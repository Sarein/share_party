package com.partymakers.shareparty.application.party.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.partymakers.shareparty.domain.friends.entity.Friend

data class PartyFriends(
    @JsonProperty("friends")
    val friends: Set<Friend>
) 