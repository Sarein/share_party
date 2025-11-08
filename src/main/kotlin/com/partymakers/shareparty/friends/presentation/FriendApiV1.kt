package com.partymakers.shareparty.friends.presentation

import com.partymakers.shareparty.friends.presentation.dto.FriendDescriptionDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus

@Tag(
    name = "Friends API",
    description = "API методы для работы с друзьями"
)
internal interface FriendApiV1 {

    @Operation(
        summary = "Зарегистрировать нового друга",
        description = "Создает запись нового друга в системе"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Друг успешно зарегистрирован"),
        ApiResponse(responseCode = "400", description = "Неверные входные данные"),
        ApiResponse(responseCode = "409", description = "Друг с таким никнеймом уже существует")
    ])
    @PostMapping("/friend")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerFriend(@RequestBody dto: FriendDescriptionDto): Unit

    companion object {

        const val FRIEND_BASE_URL = "/v1"
    }
}