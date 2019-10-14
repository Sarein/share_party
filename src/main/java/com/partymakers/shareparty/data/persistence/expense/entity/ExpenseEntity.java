package com.partymakers.shareparty.data.persistence.expense.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Table(name = "expenses")
@AllArgsConstructor
@Entity
public class ExpenseEntity {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "cost")
    private int    cost;
    @Column(name = "count")
    private double count;
}
