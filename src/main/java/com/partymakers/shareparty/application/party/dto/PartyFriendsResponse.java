package com.partymakers.shareparty.application.party.dto;

import com.partymakers.shareparty.domain.friends.entity.Friend;

import java.util.Set;

import lombok.Getter;

@Getter
public class PartyFriendsResponse {
    private Set<Friend> friends;

    public PartyFriendsResponse(Set<Friend> friends) {
        this.friends = friends;
    }
}
