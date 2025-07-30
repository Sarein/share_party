package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoom

internal interface InviteFriendsUseCase {
    operator fun invoke(roomId: Long, nickName: List<String>): PartyRoom
} 