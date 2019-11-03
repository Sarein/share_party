package com.partymakers.shareparty.application.friends.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendDescription {
    @JsonProperty("name")
    private String name;
    @JsonProperty("nickName")
    private String nickName;
    @JsonProperty("eMail")
    private String eMail;
}
