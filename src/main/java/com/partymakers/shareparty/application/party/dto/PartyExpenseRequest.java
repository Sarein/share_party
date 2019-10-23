package com.partymakers.shareparty.application.party.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PartyExpenseRequest {
    private String name;
    private int    cost;
    private double count;

    public PartyExpenseRequest(@JsonProperty("name")String name,
                               @JsonProperty("cost")int cost,
                               @JsonProperty("count")double count) {
        this.name  = name;
        this.cost  = cost;
        this.count = count;
    }
}
