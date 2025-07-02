package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.Expense

interface AddPartyExpenseUseCase {
    fun addPartyExpense(expense: Expense, partyId: Long)
} 