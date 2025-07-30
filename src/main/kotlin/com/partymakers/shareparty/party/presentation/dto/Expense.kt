package com.partymakers.shareparty.party.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty

internal data class ExpenseDto(
    @JsonProperty("id")
    val id: String?,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("cost")
    val cost: Int,
    @JsonProperty("count")
    val count: Double
) 