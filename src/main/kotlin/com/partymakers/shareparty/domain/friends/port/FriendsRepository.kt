package com.partymakers.shareparty.domain.friends.port

import com.partymakers.shareparty.domain.friends.entity.Friend

interface FriendsRepository {
    fun save(friend: Friend): Friend
    fun findOneById(nickName: String): Friend?
}