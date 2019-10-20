package com.partymakers.shareparty.domain.party.usecase.impl;

import com.partymakers.shareparty.domain.expenses.entity.Expense;
import com.partymakers.shareparty.domain.expenses.usecase.AddExpense;
import com.partymakers.shareparty.domain.party.usecase.AddPartyExpense;
import com.partymakers.shareparty.domain.usecases.party.port.PartyExpensesRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddPartyExpenseImpl implements AddPartyExpense {

    private final PartyExpensesRepository partyExpensesRepository;
    private final AddExpense expenses;

    @Override
    public long addPartyExpense(Expense expense, long partyId) {
        long expenseId = expenses.addExpense(expense);
        partyExpensesRepository.addExpense(expenseId, partyId);
        return expenseId;
    }
}
