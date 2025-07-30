package com.partymakers.shareparty.party.presentation.dto

import com.partymakers.shareparty.friends.domain.entity.Friend
import com.partymakers.shareparty.party.domain.entity.Expense

internal data class PartyRoomDto(
    val id: Long,
    val name: String,
    val friends: Set<Friend>,
    val expenses: List<Expense>
)