package com.partymakers.shareparty.application.party.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PartyRoomDescription {
    @JsonProperty("name")
    String name;
}
