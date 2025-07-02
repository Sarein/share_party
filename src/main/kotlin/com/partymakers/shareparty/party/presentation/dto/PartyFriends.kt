package com.partymakers.shareparty.party.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.partymakers.shareparty.friends.domain.entity.Friend

data class PartyFriends(
    @JsonProperty("friends")
    val friends: Set<Friend>
) 