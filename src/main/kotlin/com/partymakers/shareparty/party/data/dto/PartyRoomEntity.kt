package com.partymakers.shareparty.party.data.dto

internal data class PartyRoomEntity(
    val description: PartyRoomDescriptionEntity,
    val friends: Set<FriendEntity> = setOf(),
    val expenses: List<ExpenseEntity> = listOf(),
)