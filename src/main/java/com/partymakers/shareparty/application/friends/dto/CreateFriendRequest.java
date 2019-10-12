package com.partymakers.shareparty.application.friends.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateFriendRequest {

    String name;
    String nickName;
    String eMail;

    public CreateFriendRequest(@JsonProperty("name")     String name,
                               @JsonProperty("nickName") String nickName,
                               @JsonProperty("eMail") String eMail) {
        this.name = name;
        this.nickName = nickName;
        this.eMail = eMail;
    }
}
