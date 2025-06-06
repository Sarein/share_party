package com.partymakers.shareparty.domain.party.entity

import com.partymakers.shareparty.domain.friends.entity.Friend

data class PartyRoom(
    val description: PartyRoomDescription,
    var friends: MutableSet<Friend> = mutableSetOf(),
    var expenses: MutableList<Expense> = mutableListOf()
) {
    constructor(name: String) : this(
        description = PartyRoomDescription(name),
        friends = mutableSetOf(),
        expenses = mutableListOf()
    )

    constructor(id: Long, name: String, friends: Set<Friend>, expenses: List<Expense>) : this(
        description = PartyRoomDescription(id, name),
        friends = friends.toMutableSet(),
        expenses = expenses.toMutableList()
    )

    constructor(id: Long, name: String) : this(
        description = PartyRoomDescription(id, name),
        friends = mutableSetOf(),
        expenses = mutableListOf()
    )
} 