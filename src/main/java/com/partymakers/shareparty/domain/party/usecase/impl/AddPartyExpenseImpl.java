package com.partymakers.shareparty.domain.party.usecase.impl;

import com.partymakers.shareparty.domain.party.entity.Expense;
import com.partymakers.shareparty.domain.party.entity.PartyRoom;
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;
import com.partymakers.shareparty.domain.party.usecase.AddPartyExpense;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddPartyExpenseImpl implements AddPartyExpense {

    private final PartyRoomRepository repository;

    @Override
    public void addPartyExpense(Expense expense, long partyId) {
        PartyRoom room = repository.findById(partyId);
        room.getExpenses().add(expense);
        repository.save(room);
    }
}
