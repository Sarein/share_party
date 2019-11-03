package com.partymakers.shareparty.application.party.dto;

import com.partymakers.shareparty.domain.friends.entity.Friend;
import com.partymakers.shareparty.domain.party.entity.Expense;
import com.partymakers.shareparty.domain.party.entity.PartyRoom;

import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FullPartyInfo {
    private Long          id;
    private String        name;
    private Set<Friend>   friends;
    private List<Expense> expenses;

    public FullPartyInfo(PartyRoom partyRoom) {
        id      = partyRoom.getDescription().getId();
        name     = partyRoom.getDescription().getName();
        friends  = partyRoom.getFriends();
        expenses = partyRoom.getExpenses();
    }
}
