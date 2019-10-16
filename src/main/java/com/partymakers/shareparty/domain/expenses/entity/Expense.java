package com.partymakers.shareparty.domain.expenses.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Expense {
    long   id;
    final  String name;
    int    cost;
    double count;

    public Expense(String name, int cost, double count) {
        this.name = name;
        this.cost = cost;
        this.count = count;
    }
}
