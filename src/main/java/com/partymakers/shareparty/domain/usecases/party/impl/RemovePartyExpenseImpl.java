package com.partymakers.shareparty.domain.usecases.party.impl;

import com.partymakers.shareparty.domain.usecases.party.RemovePartyExpense;
import com.partymakers.shareparty.domain.usecases.party.port.PartyExpensesRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemovePartyExpenseImpl implements RemovePartyExpense {

    private final PartyExpensesRepository partyExpensesRepository;

    @Override
    public void removePartyExpense(long expenseId, long partyId) {
        //TODO: удалять из  таблички с расходами, но нужно-ли?
        partyExpensesRepository.deleteExpense(expenseId, partyId);
    }
}
