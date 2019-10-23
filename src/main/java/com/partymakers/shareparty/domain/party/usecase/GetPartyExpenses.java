package com.partymakers.shareparty.domain.party.usecase;

import com.partymakers.shareparty.domain.party.entity.Expense;

import java.util.List;

public interface GetPartyExpenses {
    List<Expense>  getPartyExpenses(Long partyId);
}
