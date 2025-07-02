package com.partymakers.shareparty.domain.friends.usecase

import com.partymakers.shareparty.domain.friends.entity.Friend
import com.partymakers.shareparty.domain.friends.port.FriendsRepository
import com.partymakers.shareparty.domain.party.usecase.exception.AlreadyExistException
import org.springframework.stereotype.Service

@Service
class RegisterFriendImpl(
    private val repository: FriendsRepository
) : RegisterFriend {

    override fun registerFriend(friend: Friend) {
        repository.findOneById(friend.nickName)
            ?.let { foundFriend -> throw AlreadyExistException("Friend " + friend.name + " already exist") }
            ?: run { repository.save(friend) }
    }

}