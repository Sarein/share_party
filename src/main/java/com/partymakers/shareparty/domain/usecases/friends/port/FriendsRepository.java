package com.partymakers.shareparty.domain.usecases.friends.port;

import com.partymakers.shareparty.domain.entity.Friend;
import org.springframework.data.repository.CrudRepository;

public interface FriendsRepository extends CrudRepository<Friend, Long> {
}
