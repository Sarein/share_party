package com.partymakers.shareparty.domain.party.usecase;

import com.partymakers.shareparty.domain.party.entity.Expense;

public interface RemovePartyExpense {
    void removePartyExpense(Expense expense, long partyId);
}
