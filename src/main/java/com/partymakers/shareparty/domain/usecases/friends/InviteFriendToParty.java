package com.partymakers.shareparty.domain.usecases.friends;

import com.partymakers.shareparty.domain.entity.Friend;

public interface InviteFriendToParty {
    void inviteFriendToParty(Friend friend, long partyId);
}
