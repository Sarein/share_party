package com.partymakers.shareparty.data.persistence.expense.entity;

import com.partymakers.shareparty.data.persistence.DomainMapper;
import com.partymakers.shareparty.data.persistence.PersistenceMapper;
import com.partymakers.shareparty.domain.entity.Expense;

public class ExpenseMapping implements DomainMapper<Expense, ExpenseEntity>, PersistenceMapper<ExpenseEntity, Expense> {

    @Override
    public ExpenseEntity toPersistence(Expense domainEntity) {
        return new ExpenseEntity(domainEntity.getUId(),
            domainEntity.getName(),
            domainEntity.getCost(),
            domainEntity.getCount()
        );
    }

    @Override
    public Expense toDomain(ExpenseEntity persistenceEntity) {
         return new Expense(persistenceEntity.getId(),
            persistenceEntity.getName(),
            persistenceEntity.getCost(),
            persistenceEntity.getCount()
        );
    }
}

