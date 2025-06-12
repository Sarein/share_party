package com.partymakers.shareparty.domain.party.usecase

import com.partymakers.shareparty.domain.party.entity.Expense

interface AddPartyExpense {
    fun addPartyExpense(expense: Expense, partyId: Long)
} 