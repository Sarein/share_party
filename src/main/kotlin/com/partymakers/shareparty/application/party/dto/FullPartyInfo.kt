package com.partymakers.shareparty.application.party.dto

import com.partymakers.shareparty.domain.friends.entity.Friend
import com.partymakers.shareparty.domain.party.entity.Expense
import com.partymakers.shareparty.domain.party.entity.PartyRoom

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