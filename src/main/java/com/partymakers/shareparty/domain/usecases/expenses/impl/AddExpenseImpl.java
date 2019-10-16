package com.partymakers.shareparty.domain.usecases.expenses.impl;

import com.partymakers.shareparty.domain.entity.Expense;
import com.partymakers.shareparty.domain.usecases.expenses.AddExpense;

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
