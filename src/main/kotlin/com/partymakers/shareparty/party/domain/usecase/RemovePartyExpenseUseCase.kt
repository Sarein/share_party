package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoom

internal interface RemovePartyExpenseUseCase {
    operator fun invoke(partyId: Long, expenseId: Long): PartyRoom
} 