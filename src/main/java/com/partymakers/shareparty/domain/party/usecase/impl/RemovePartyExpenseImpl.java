package com.partymakers.shareparty.domain.party.usecase.impl;

import com.partymakers.shareparty.domain.party.entity.Expense;
import com.partymakers.shareparty.domain.party.entity.PartyRoom;
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;
import com.partymakers.shareparty.domain.party.usecase.RemovePartyExpense;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemovePartyExpenseImpl implements RemovePartyExpense {

    private final PartyRoomRepository partyExpensesRepository;

    @Override
    public void removePartyExpense(Long partyId, Expense expense) {
        PartyRoom partyRoom = partyExpensesRepository.findById(partyId);
        partyRoom.getExpenses().removeIf(partyExpense -> partyExpense.equals(expense));
        partyExpensesRepository.save(partyRoom);
    }
}
