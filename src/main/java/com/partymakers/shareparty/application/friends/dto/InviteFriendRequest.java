package com.partymakers.shareparty.application.friends.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InviteFriendRequest {
    String nickName;

    public InviteFriendRequest(@JsonProperty("nickName") String nickName) {
        this.nickName = nickName;
    }
}
