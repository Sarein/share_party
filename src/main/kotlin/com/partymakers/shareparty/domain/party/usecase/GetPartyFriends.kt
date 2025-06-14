package com.partymakers.shareparty.domain.party.usecase

import com.partymakers.shareparty.domain.friends.entity.Friend

interface GetPartyFriends {
    fun getPartyFriends(partyId: Long): Set<Friend>
} 