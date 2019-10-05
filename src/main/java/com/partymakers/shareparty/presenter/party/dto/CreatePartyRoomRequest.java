package com.partymakers.shareparty.presenter.party.dto;

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

    @Override
    public String toString() {
        return "{" +
            "name='" + name + '\'' +
            '}';
    }
}
