package com.partymakers.shareparty.friends.domain.usecase

interface KickFriendUseCase {
    fun kickFriend(nickName: String, partyId: Long)
} 