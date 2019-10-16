package com.partymakers.shareparty.application.configuration;


import com.partymakers.shareparty.data.persistence.DomainMapper;
import com.partymakers.shareparty.data.persistence.PersistenceMapper;
import com.partymakers.shareparty.data.persistence.RepositoryFacade;
import com.partymakers.shareparty.data.persistence.expense.entity.ExpenseEntity;
import com.partymakers.shareparty.data.persistence.expense.entity.ExpenseMapping;
import com.partymakers.shareparty.domain.expenses.entity.Expense;
import com.partymakers.shareparty.domain.expenses.usecase.AddExpense;
import com.partymakers.shareparty.domain.expenses.usecase.impl.AddExpenseImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

@Configuration
@EnableJpaRepositories("com.partymakers.shareparty.data.persistence.expense")
public class ExpensesConfiguration {

    static final ExpenseMapping expenseMapping =  new ExpenseMapping();

    @Bean
    PersistenceMapper<ExpenseEntity, Expense> expensePersistenceMapper() { return expenseMapping; }

    @Bean
    DomainMapper<Expense, ExpenseEntity> expenseDomainMapper() { return expenseMapping; }

    @Bean
    CrudRepository<Expense, Long> expenseRepository(JpaRepository<ExpenseEntity, Long> repository) {
        return new RepositoryFacade<>(repository, expenseDomainMapper(), expensePersistenceMapper());
    }

    @Bean
    AddExpense addExpense(@Autowired JpaRepository<ExpenseEntity, Long> repository) {
        return new AddExpenseImpl(expenseRepository(repository));
    }
}
