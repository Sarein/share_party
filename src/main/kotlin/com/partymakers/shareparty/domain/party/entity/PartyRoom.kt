package com.partymakers.shareparty.domain.party.entity

import com.partymakers.shareparty.domain.friends.entity.Friend

data class PartyRoom(
    val description: PartyRoomDescription,
    val friends: Set<Friend> = setOf(),
    val expenses: List<Expense> = listOf()
) {
    constructor(name: String) : this(
        description = PartyRoomDescription(name),
        friends = setOf(),
        expenses = listOf()
    )

    constructor(id: Long, name: String, friends: Set<Friend>, expenses: List<Expense>) : this(
        description = PartyRoomDescription(id, name),
        friends = friends,
        expenses = expenses
    )

    constructor(id: Long, name: String) : this(
        description = PartyRoomDescription(id, name),
        friends = setOf(),
        expenses = listOf()
    )
} 