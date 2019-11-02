package com.partymakers.shareparty.application.party.dto;

import com.partymakers.shareparty.domain.friends.entity.Friend;
import com.partymakers.shareparty.domain.party.entity.Expense;
import com.partymakers.shareparty.domain.party.entity.PartyRoom;

import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class FullPartyInfo {
    private final Long          id;
    private final String        name;
    private final Set<Friend>   friends;
    private final List<Expense> expenses;

    public FullPartyInfo(PartyRoom partyRoom) {
        id      = partyRoom.getDescription().getId();
        name     = partyRoom.getDescription().getName();
        friends  = partyRoom.getFriends();
        expenses = partyRoom.getExpenses();
    }
}
