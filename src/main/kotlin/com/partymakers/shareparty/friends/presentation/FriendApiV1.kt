package com.partymakers.shareparty.friends.presentation

import com.partymakers.shareparty.friends.presentation.dto.FriendDescriptionDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus

internal interface FriendApiV1 {

    @PostMapping("/friend")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerFriend(@RequestBody dto: FriendDescriptionDto): Unit

    companion object {

        const val FRIEND_BASE_URL = "/v1"
    }
}