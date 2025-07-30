package com.partymakers.shareparty.party.domain.entity

internal data class Expense(
    val id: Long?,
    val name: String,
    val cost: Int,
    val count: Double,
) 