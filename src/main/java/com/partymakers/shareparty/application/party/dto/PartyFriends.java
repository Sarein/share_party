package com.partymakers.shareparty.application.party.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.partymakers.shareparty.domain.friends.entity.Friend;

import java.util.Set;

import lombok.Data;
@Data
public class PartyFriends {
    @JsonProperty("friends")
    private final Set<Friend> friends;
}
