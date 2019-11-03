package com.partymakers.shareparty.data.persistence.friends.impl;

import com.partymakers.shareparty.data.persistence.friends.FriendPersistenceRepository;
import com.partymakers.shareparty.data.persistence.friends.entity.FriendEntity;
import com.partymakers.shareparty.domain.friends.entity.Friend;
import com.partymakers.shareparty.domain.friends.port.FriendsRepository;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FriendsRepositoryImpl implements FriendsRepository {

    private final FriendPersistenceRepository repository;

    @Override
    public Friend save(Friend friend) {
        return repository.save(FriendEntity.toPersistence(friend)).toDomain();
    }

    @Override
    public Optional<Friend> findOneById(String nickName) {
        return repository.findById(nickName).flatMap(friendEntity -> Optional.of(friendEntity.toDomain()));
    }
}
