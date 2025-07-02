package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.friends.domain.entity.Friend

interface GetPartyFriendsUseCase {
    fun getPartyFriends(partyId: Long): Set<Friend>
} 