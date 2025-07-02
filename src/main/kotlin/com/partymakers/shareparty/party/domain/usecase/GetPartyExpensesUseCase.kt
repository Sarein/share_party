package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.Expense

interface GetPartyExpensesUseCase {
    fun getPartyExpenses(partyId: Long): List<Expense>
} 