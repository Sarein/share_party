package com.partymakers.shareparty.domain.party.usecase;

import com.partymakers.shareparty.domain.friends.entity.Friend;

import java.util.List;
import java.util.Set;

public interface GetPartyFriends {
    Set<Friend> getPartyFriends(long partyId);
}
