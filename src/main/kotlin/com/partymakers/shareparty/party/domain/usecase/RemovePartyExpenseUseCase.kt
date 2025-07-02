package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.Expense

interface RemovePartyExpenseUseCase {
    fun removePartyExpense(partyId: Long, expense: Expense)
} 