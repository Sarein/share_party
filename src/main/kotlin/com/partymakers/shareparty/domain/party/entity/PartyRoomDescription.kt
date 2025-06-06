package com.partymakers.shareparty.domain.party.entity

data class PartyRoomDescription(
    val id: Long,
    val name: String
) {
    constructor(name: String) : this(0L, name)
} 