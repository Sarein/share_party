package com.partymakers.shareparty.domain.usecases.expenses;

import com.partymakers.shareparty.domain.entity.Expense;

public interface AddExpense {
    Long addExpense(Expense expense);
}
