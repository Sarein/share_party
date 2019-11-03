package com.partymakers.shareparty.domain.party.usecase.impl;

import com.partymakers.shareparty.domain.party.entity.Expense;
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;
import com.partymakers.shareparty.domain.party.usecase.AddPartyExpense;
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddPartyExpenseImpl implements AddPartyExpense {

    private final PartyRoomRepository repository;

    @Override
    public void addPartyExpense(Expense expense, long partyId) {
        repository.findById(partyId).ifPresentOrElse( partyRoom -> {
            partyRoom.getExpenses().add(expense); repository.save(partyRoom); },
            () ->{throw new NotFoundException("Party room with id: " + partyId + "not found");} );
    }
}
