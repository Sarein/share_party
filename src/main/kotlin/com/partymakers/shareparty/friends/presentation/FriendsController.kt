package com.partymakers.shareparty.friends.presentation

import com.partymakers.shareparty.friends.domain.usecase.RegisterFriendUseCase
import com.partymakers.shareparty.friends.presentation.dto.FriendDescriptionDto
import com.partymakers.shareparty.friends.presentation.mapper.FriendDtoMapper
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(FriendApiV1.FRIEND_BASE_URL)
internal class FriendsController(
    private val registerUseCase: RegisterFriendUseCase,
    private val friendMapper: FriendDtoMapper,
) : FriendApiV1 {

    override fun registerFriend(dto: FriendDescriptionDto) {
        val model = friendMapper.toModel(dto)
        registerUseCase.registerFriend(model)
    }
}