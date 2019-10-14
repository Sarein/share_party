package com.partymakers.shareparty.data.persistence.expense;

import com.partymakers.shareparty.data.persistence.expense.entity.ExpenseEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensePersistenceRepository extends JpaRepository<ExpenseEntity, Long> {
}
