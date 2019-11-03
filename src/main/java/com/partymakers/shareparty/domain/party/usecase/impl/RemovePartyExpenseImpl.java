package com.partymakers.shareparty.domain.party.usecase.impl;

import com.partymakers.shareparty.domain.party.entity.Expense;
import com.partymakers.shareparty.domain.party.entity.PartyRoom;
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;
import com.partymakers.shareparty.domain.party.usecase.RemovePartyExpense;
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemovePartyExpenseImpl implements RemovePartyExpense {

    private final PartyRoomRepository partyRoomRepository;

    @Override
    public void removePartyExpense(Long partyId, Expense expense) {
        partyRoomRepository.findById(partyId).ifPresentOrElse(
            partyRoom -> {
                partyRoom.getExpenses().removeIf(partyExpense -> partyExpense.equals(expense));
                partyRoomRepository.save(partyRoom);
            },
            () -> {throw new NotFoundException();});
    }
}
