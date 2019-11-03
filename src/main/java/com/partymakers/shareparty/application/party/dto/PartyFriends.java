package com.partymakers.shareparty.application.party.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.partymakers.shareparty.domain.friends.entity.Friend;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyFriends {
    @JsonProperty("friends")
    private Set<Friend> friends;
}
