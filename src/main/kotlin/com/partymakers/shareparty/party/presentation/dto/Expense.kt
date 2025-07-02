package com.partymakers.shareparty.party.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Expense(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("cost")
    val cost: Int,
    @JsonProperty("count")
    val count: Double
) 