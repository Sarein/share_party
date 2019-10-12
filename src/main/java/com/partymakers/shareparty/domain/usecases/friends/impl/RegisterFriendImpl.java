package com.partymakers.shareparty.domain.usecases.friends.impl;

import com.partymakers.shareparty.domain.entity.Friend;
import com.partymakers.shareparty.domain.usecases.friends.RegisterFriend;

import org.springframework.data.repository.CrudRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterFriendImpl implements RegisterFriend {
    private final CrudRepository<Friend, String> jpaRepository;

    @Override
    public void registerFriend(Friend friend) {
        jpaRepository.save(friend);
    }
}
