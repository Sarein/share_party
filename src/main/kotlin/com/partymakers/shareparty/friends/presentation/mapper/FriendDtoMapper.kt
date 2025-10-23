package com.partymakers.shareparty.friends.presentation.mapper

import com.partymakers.shareparty.friends.domain.entity.Friend
import com.partymakers.shareparty.friends.presentation.dto.FriendDescriptionDto
import org.springframework.stereotype.Component

@Component
internal class FriendDtoMapper {

    fun toModel(dto: FriendDescriptionDto): Friend =
        Friend(
            name = dto.name,
            nickName = dto.nickName,
            mail = dto.mail,
        )
}