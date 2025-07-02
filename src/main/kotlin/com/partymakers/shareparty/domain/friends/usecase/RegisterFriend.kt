package com.partymakers.shareparty.domain.friends.usecase

import com.partymakers.shareparty.domain.friends.entity.Friend
import org.springframework.stereotype.Service


interface RegisterFriend {
    fun registerFriend(friend: Friend)
}