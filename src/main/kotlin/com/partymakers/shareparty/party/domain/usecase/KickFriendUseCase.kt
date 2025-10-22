package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoom

internal interface KickFriendUseCase {
    operator fun invoke(roomId: Long, nickName: String): PartyRoom
} 