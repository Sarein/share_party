package com.partymakers.shareparty.domain.expenses.usecase.impl;

import com.partymakers.shareparty.domain.expenses.entity.Expense;
import com.partymakers.shareparty.domain.expenses.usecase.AddExpense;

import org.springframework.data.repository.CrudRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddExpenseImpl implements AddExpense {

    private final CrudRepository<Expense, Long> repository;

    @Override
    public Long addExpense(Expense expense) {
        return repository.save(expense).getId();
    }
}
