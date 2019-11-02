package com.partymakers.shareparty.application.friends.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FriendDescription {
    @JsonProperty("name")
    private final String name;
    @JsonProperty("nickName")
    private final String nickName;
    @JsonProperty("eMail")
    private final String eMail;
}
