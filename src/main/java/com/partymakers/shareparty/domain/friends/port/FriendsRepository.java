package com.partymakers.shareparty.domain.friends.port;

import com.partymakers.shareparty.domain.friends.entity.Friend;
import org.springframework.data.repository.CrudRepository;

public interface FriendsRepository extends CrudRepository<Friend, Long> {
}
