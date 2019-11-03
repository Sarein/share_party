package com.partymakers.shareparty.domain.friends.port;

import com.partymakers.shareparty.domain.friends.entity.Friend;

import java.util.Optional;

public interface FriendsRepository {
    Friend save(Friend friend);
    Optional<Friend> findOneById(String nickName);
}
