package com.partymakers.shareparty.friends.domain.usecase

interface InviteFriendUseCase {
    fun inviteFriend(nickName: String, partyId: Long)
} 