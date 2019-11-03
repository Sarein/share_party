package com.partymakers.shareparty.application.party.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.partymakers.shareparty.domain.party.entity.Expense;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyExpenses {
    @JsonProperty("expenses")
    private List<Expense> partyExpenses;
}
