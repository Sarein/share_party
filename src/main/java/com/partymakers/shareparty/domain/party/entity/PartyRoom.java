package com.partymakers.shareparty.domain.party.entity;

import com.partymakers.shareparty.domain.expenses.entity.Expense;
import com.partymakers.shareparty.domain.friends.entity.Friend;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PartyRoom {

    @Getter
    final PartyRoomDescription description;

    @Getter
    @Setter
    List<Friend>  friends;

    @Getter
    @Setter
    List<Expense> expenses;

    public PartyRoom(String name){
        description = new PartyRoomDescription(0, name);
        friends     = new ArrayList<>();
        expenses    = new ArrayList<>();
    }

    public PartyRoom(long id, String name){
        description = new PartyRoomDescription(id, name);
        friends     = new ArrayList<>();
        expenses    = new ArrayList<>();
    }
}
