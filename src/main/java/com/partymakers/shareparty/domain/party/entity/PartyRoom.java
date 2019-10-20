package com.partymakers.shareparty.domain.party.entity;

import com.partymakers.shareparty.domain.expenses.entity.Expense;
import com.partymakers.shareparty.domain.friends.entity.Friend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public class PartyRoom {

    @Getter
    final PartyRoomDescription description;

    @Getter
    @Setter
    //many-to-many
    Set<Friend> friends;

    @Getter
    @Setter
    //many-to-one
    Set<Expense> expenses;

    public PartyRoom(String name){
        description = new PartyRoomDescription(0, name);
        friends     = new HashSet<>();
        expenses    = new HashSet<>();
    }

    public PartyRoom(long id, String name, Set<Friend>  friends){
        description = new PartyRoomDescription(id, name);
        this.friends     = friends;
        this.expenses    = new HashSet<>();
    }

    public PartyRoom(long id, String name){
        description = new PartyRoomDescription(id, name);
        friends     = new HashSet<>();
        expenses    = new HashSet<>();
    }
}
