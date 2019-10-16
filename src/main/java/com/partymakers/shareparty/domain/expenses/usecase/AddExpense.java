package com.partymakers.shareparty.domain.expenses.usecase;

import com.partymakers.shareparty.domain.expenses.entity.Expense;

public interface AddExpense {
    Long addExpense(Expense expense);
}
