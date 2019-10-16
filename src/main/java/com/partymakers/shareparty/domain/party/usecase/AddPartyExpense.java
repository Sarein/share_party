package com.partymakers.shareparty.domain.party.usecase;

import com.partymakers.shareparty.domain.expenses.entity.Expense;

public interface AddPartyExpense {
    long addPartyExpense(Expense expense, long partyId);
}
