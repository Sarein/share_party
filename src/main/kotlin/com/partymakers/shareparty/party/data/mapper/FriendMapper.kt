package com.partymakers.shareparty.party.data.mapper

import com.partymakers.shareparty.friends.domain.entity.Friend
import com.partymakers.shareparty.party.data.dto.FriendEntity
import org.springframework.stereotype.Component

@Component
internal class FriendMapper {

    fun toEntity(dto: FriendEntity): Friend =
        Friend(
            nickName = dto.nickName,
            name = dto.name,
            eMail = dto.eMail ?: "",
        )
}