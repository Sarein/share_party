package com.partymakers.shareparty.data.persistence.party.impl;

import com.partymakers.shareparty.data.persistence.party.PartyExpensesEntityRepository;
import com.partymakers.shareparty.data.persistence.party.entity.PartyExpensesEntity;
import com.partymakers.shareparty.domain.expenses.entity.Expense;
import com.partymakers.shareparty.domain.usecases.party.port.PartyExpensesRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExpensesRepositoryImpl implements PartyExpensesRepository {

    private final PartyExpensesEntityRepository repository;

    @Override
    public void addExpense(long expenseUid, long partyRoom) {
        repository.save(new PartyExpensesEntity(expenseUid, partyRoom));
    }

    @Override
    public void deleteExpense(long expenseUid, long partyRoom) {
        repository.delete(expenseUid, partyRoom);
    }

    @Override
    public Iterable<Expense> getExpenses(long partyRoom) {
        return null;
    }
}
