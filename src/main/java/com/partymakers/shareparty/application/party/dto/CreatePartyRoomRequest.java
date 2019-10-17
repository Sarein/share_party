package com.partymakers.shareparty.application.party.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePartyRoomRequest{
    String name;

    public CreatePartyRoomRequest(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
