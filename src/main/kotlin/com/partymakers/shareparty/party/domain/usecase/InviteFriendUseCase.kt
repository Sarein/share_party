package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoom

internal interface InviteFriendUseCase {
    operator fun invoke(nickName: String, roomId: Long): PartyRoom
} 