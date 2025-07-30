package com.partymakers.shareparty.party.data.dto

import com.fasterxml.jackson.annotation.JsonProperty

internal data class ExpenseEntity(
    @JsonProperty("expense_id")
    val id: Long,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("cost")
    val cost: Int,
    @JsonProperty("count")
    val count: Double,
) 