package com.partymakers.shareparty.friends.domain.repository

import com.partymakers.shareparty.friends.domain.entity.Friend

interface FriendsRepository {
    fun save(friend: Friend): Friend?
    fun findOneById(nickName: String): Friend?
}