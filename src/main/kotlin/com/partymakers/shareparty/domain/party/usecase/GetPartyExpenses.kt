package com.partymakers.shareparty.domain.party.usecase

import com.partymakers.shareparty.domain.party.entity.Expense

interface GetPartyExpenses {
    fun getPartyExpenses(partyId: Long): List<Expense>
} 