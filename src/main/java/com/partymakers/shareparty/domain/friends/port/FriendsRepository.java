package com.partymakers.shareparty.domain.friends.port;

import com.partymakers.shareparty.domain.friends.entity.Friend;

public interface FriendsRepository {
    Friend save(Friend friend);
    Friend findOneById(String nickName);
}
