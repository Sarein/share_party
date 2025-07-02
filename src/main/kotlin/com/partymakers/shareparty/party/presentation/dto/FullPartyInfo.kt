package com.partymakers.shareparty.party.presentation.dto

import com.partymakers.shareparty.friends.domain.entity.Friend
import com.partymakers.shareparty.party.domain.entity.Expense
import com.partymakers.shareparty.party.domain.entity.PartyRoom

data class FullPartyInfo(
    val id: Long,
    val name: String,
    val friends: Set<Friend>,
    val expenses: List<Expense>
) {
    constructor(partyRoom: PartyRoom) : this(
        id = partyRoom.description.id,
        name = partyRoom.description.name,
        friends = partyRoom.friends,
        expenses = partyRoom.expenses
    )
} 