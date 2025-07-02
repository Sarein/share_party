package com.partymakers.shareparty.party.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.partymakers.shareparty.party.domain.entity.Expense

data class PartyExpenses(
    @JsonProperty("expenses")
    val partyExpenses: List<Expense>
) 