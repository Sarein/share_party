package com.partymakers.shareparty.domain.party.usecase;

import com.partymakers.shareparty.domain.party.entity.Expense;

public interface AddPartyExpense {
    void addPartyExpense(Expense expense, long partyId);
}
