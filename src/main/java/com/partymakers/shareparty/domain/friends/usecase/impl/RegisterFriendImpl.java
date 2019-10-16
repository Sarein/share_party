package com.partymakers.shareparty.domain.friends.usecase.impl;

import com.partymakers.shareparty.domain.friends.entity.Friend;
import com.partymakers.shareparty.domain.friends.usecase.RegisterFriend;

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
