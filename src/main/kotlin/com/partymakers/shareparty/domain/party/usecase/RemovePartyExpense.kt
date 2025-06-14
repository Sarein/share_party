package com.partymakers.shareparty.domain.party.usecase

import com.partymakers.shareparty.domain.party.entity.Expense

interface RemovePartyExpense {
    fun removePartyExpense(partyId: Long, expense: Expense)
} 