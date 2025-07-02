package com.partymakers.shareparty.friends.domain.usecase

import com.partymakers.shareparty.party.domain.exception.AlreadyExistException
import com.partymakers.shareparty.friends.domain.repository.FriendsRepository
import com.partymakers.shareparty.friends.domain.entity.Friend
import org.springframework.stereotype.Service

@Service
class RegisterFriendUseCaseImpl(
    private val repository: FriendsRepository
) : RegisterFriendUseCase {

    override fun registerFriend(friend: Friend) {
        repository.findOneById(friend.nickName)
            ?.let { foundFriend -> throw AlreadyExistException("Friend " + friend.name + " already exist") }
            ?: run { repository.save(friend) }
    }

}