package com.partymakers.shareparty.application.friends

import com.partymakers.shareparty.application.V1Controller
import com.partymakers.shareparty.application.friends.dto.FriendDescription
import com.partymakers.shareparty.domain.friends.entity.Friend
import com.partymakers.shareparty.domain.friends.usecase.RegisterFriend
import com.partymakers.shareparty.domain.party.usecase.exception.AlreadyExistException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FriendsController(
    private val registerUseCase: RegisterFriend
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