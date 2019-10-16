package com.partymakers.shareparty.domain.usecases.party;

public interface RemovePartyExpense {
    void removePartyExpense(long expenseId, long partyId);
}
