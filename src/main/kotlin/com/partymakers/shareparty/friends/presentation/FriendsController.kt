package com.partymakers.shareparty.friends.presentation

import com.partymakers.shareparty.V1Controller
import com.partymakers.shareparty.friends.presentation.dto.FriendDescription
import com.partymakers.shareparty.friends.domain.entity.Friend
import com.partymakers.shareparty.friends.domain.usecase.RegisterFriendUseCase
import com.partymakers.shareparty.party.domain.exception.AlreadyExistException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FriendsController(
    private val registerUseCase: RegisterFriendUseCase
) : V1Controller() {

    @PostMapping("/friend")
    fun registerFriend(
        @RequestBody request: FriendDescription
    ): ResponseEntity<out Any?> =
        try {
            registerUseCase.registerFriend(
                Friend(
                    request.name,
                    request.nickName,
                    request.eMail
                )
            )
            ResponseEntity<Void>(HttpStatus.CREATED)
        } catch (e: AlreadyExistException) {
            ResponseEntity(e.message, HttpStatus.ALREADY_REPORTED)
        }
}