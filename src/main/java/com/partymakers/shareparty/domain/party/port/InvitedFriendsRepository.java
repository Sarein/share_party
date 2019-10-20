package com.partymakers.shareparty.domain.party.port;

import com.partymakers.shareparty.domain.friends.entity.Friend;

import java.util.List;

public interface InvitedFriendsRepository {
    void inviteFriend(String nickName, Long partyRoom);
    void deleteFriend(String nickName, Long partyRoom);
    List<Friend> getPartyFriends(Long partyRoom);
}
