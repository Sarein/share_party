package com.partymakers.shareparty.domain.party.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@EqualsAndHashCode(of= {"name", "cost", "count"})
public class Expense {
    final String  name;
    final Integer cost;
    final Double  count;
}
