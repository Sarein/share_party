package com.partymakers.shareparty.domain.usecases.party.port;

import com.partymakers.shareparty.domain.entity.Expense;

public interface PartyExpensesRepository {
    void addExpense(long expenseUid, long partyRoom);
    void deleteExpense(long expenseUid, long partyRoom);
    Iterable<Expense> getExpenses(long partyRoom);
}
