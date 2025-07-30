package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.Expense
import com.partymakers.shareparty.party.domain.entity.PartyRoom

internal interface AddPartyExpenseUseCase {
    operator fun invoke(roomId: Long, expense: Expense): PartyRoom
} 