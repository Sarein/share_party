package com.partymakers.shareparty.application.party.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.partymakers.shareparty.domain.party.entity.Expense

data class PartyExpenses(
    @JsonProperty("expenses")
    val partyExpenses: List<Expense>
) 