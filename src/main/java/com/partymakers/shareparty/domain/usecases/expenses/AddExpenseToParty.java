package com.partymakers.shareparty.domain.usecases.expenses;

import com.partymakers.shareparty.domain.entity.Expense;

public interface AddExpenseToParty {
    void addExpense(Expense expense, long partyId);
}
