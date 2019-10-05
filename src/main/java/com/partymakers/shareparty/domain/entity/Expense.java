package com.partymakers.shareparty.domain.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Expense {
    final String name;
    int    cost;
    double count;


}
