package com.partymakers.shareparty.domain.usecases.party;

import com.partymakers.shareparty.domain.entity.Expense;

public interface AddPartyExpense {
    long addPartyExpense(Expense expense, long partyId);
}
