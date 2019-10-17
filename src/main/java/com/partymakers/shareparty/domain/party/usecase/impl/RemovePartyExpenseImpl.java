package com.partymakers.shareparty.domain.party.usecase.impl;

import com.partymakers.shareparty.domain.party.usecase.RemovePartyExpense;
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
