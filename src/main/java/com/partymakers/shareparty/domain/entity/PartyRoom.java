package com.partymakers.shareparty.domain.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PartyRoom {

    @Getter
    final int id;


    @Getter
    final String  name;

    @Getter
    @Setter
    List<Friend>  friends;

    @Getter
    @Setter
    List<Expense> expenses;

    public PartyRoom(String name){
        id = 0;
        this.name = name;
        friends   = new ArrayList<>();
        expenses  = new ArrayList<>();
    }

    public PartyRoom(int id, String name){
        this.id   = id;
        this.name = name;
        friends   = new ArrayList<>();
        expenses  = new ArrayList<>();
    }
}
