package com.partymakers.shareparty.friends.domain.usecase

import com.partymakers.shareparty.friends.domain.entity.Friend


interface RegisterFriendUseCase {
    fun registerFriend(friend: Friend)
}