package com.partymakers.shareparty.domain.usecases.party.impl;

import com.partymakers.shareparty.domain.entity.Expense;
import com.partymakers.shareparty.domain.usecases.expenses.AddExpense;
import com.partymakers.shareparty.domain.usecases.party.AddPartyExpense;
import com.partymakers.shareparty.domain.usecases.party.port.PartyExpensesRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddPartyExpenseImpl implements AddPartyExpense {

    private final PartyExpensesRepository partyExpensesRepository;
    private final AddExpense expenses;

    @Override
    public void addPartyExpense(Expense expense, long partyId) {
        partyExpensesRepository.addExpense(expenses.addExpense(expense), partyId);
    }
}
