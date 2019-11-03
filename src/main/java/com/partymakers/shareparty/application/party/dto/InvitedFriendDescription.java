package com.partymakers.shareparty.application.party.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitedFriendDescription {
    @JsonProperty("nickName")
    String nickName;
}
