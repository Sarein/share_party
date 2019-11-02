package com.partymakers.shareparty.application.party.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Expense {
    @JsonProperty("name")
    private final String  name;
    @JsonProperty("cost")
    private final Integer cost;
    @JsonProperty("count")
    private final Double count;
}
