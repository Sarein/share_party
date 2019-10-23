package com.partymakers.shareparty.domain.party.usecase.impl;

import com.partymakers.shareparty.domain.party.entity.Expense;
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;
import com.partymakers.shareparty.domain.party.usecase.GetPartyExpenses;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetPartyExpensesImpl implements GetPartyExpenses {

    private final PartyRoomRepository repository;

    @Override
    public List<Expense> getPartyExpenses(Long partyId) {
        return repository.findById(partyId).getExpenses();
    }
}
