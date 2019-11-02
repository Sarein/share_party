package com.partymakers.shareparty.application.party.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.partymakers.shareparty.domain.party.entity.Expense;

import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PartyExpenses {
    @JsonProperty("expenses")
    final private List<Expense> partyExpenses;
}
