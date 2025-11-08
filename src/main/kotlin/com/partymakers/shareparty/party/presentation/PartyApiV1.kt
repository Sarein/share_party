package com.partymakers.shareparty.party.presentation

import com.partymakers.common.dto.Content
import com.partymakers.shareparty.party.presentation.dto.ExpenseDto
import com.partymakers.shareparty.party.presentation.dto.PartyRoomDescriptionDto
import com.partymakers.shareparty.party.presentation.dto.PartyRoomDto
import com.partymakers.shareparty.party.presentation.dto.PartyRoomIdDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(
    name = "Party API",
    description = "API методы для работы с вечеринками, участниками и расходами"
)
internal interface PartyApiV1 {

    @Operation(
        summary = "Создать новую вечеринку",
        description = "Создает новую вечеринку с указанным описанием"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Вечеринка успешно создана"),
        ApiResponse(responseCode = "400", description = "Неверные входные данные")
    ])
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPartyRoom(@RequestBody request: PartyRoomDescriptionDto): PartyRoomIdDto

    @Operation(
        summary = "Получить список всех вечеринок",
        description = "Возвращает список всех существующих вечеринок"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Список вечеринок успешно получен")
    ])
    @GetMapping("/parties")
    @ResponseStatus(HttpStatus.OK)
    fun getPartiesRoom(): Content<PartyRoomDto>

    @Operation(
        summary = "Пригласить друга в вечеринку",
        description = "Добавляет друга с указанным никнеймом в вечеринку"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Друг успешно приглашен"),
        ApiResponse(responseCode = "404", description = "Вечеринка не найдена"),
        ApiResponse(responseCode = "400", description = "Неверные входные данные")
    ])
    @PostMapping("/{partyId}/friend")
    @ResponseStatus(HttpStatus.OK)
    fun inviteFriendToParty(
        @Parameter(description = "ID вечеринки") @PathVariable partyId: Long,
        @Parameter(description = "Никнейм друга") @RequestParam nickName: String,
    ): PartyRoomDto

    @Operation(
        summary = "Исключить друга из вечеринки",
        description = "Удаляет друга с указанным никнеймом из вечеринки"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Друг успешно исключен"),
        ApiResponse(responseCode = "404", description = "Вечеринка или друг не найдены")
    ])
    @DeleteMapping("/{partyId}/friend")
    @ResponseStatus(HttpStatus.OK)
    fun kickFriend(
        @Parameter(description = "ID вечеринки") @PathVariable partyId: Long,
        @Parameter(description = "Никнейм друга") @RequestParam nickName: String,
    ): PartyRoomDto?

    @Operation(
        summary = "Добавить расход в вечеринку",
        description = "Добавляет новый расход в указанную вечеринку"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Расход успешно добавлен"),
        ApiResponse(responseCode = "404", description = "Вечеринка не найдена"),
        ApiResponse(responseCode = "400", description = "Неверные входные данные")
    ])
    @PostMapping("/{partyId}/expense")
    @ResponseStatus(HttpStatus.CREATED)
    fun addPartyExpense(
        @Parameter(description = "ID вечеринки") @PathVariable partyId: Long,
        @RequestBody request: ExpenseDto
    ): PartyRoomDto

    @Operation(
        summary = "Удалить расход из вечеринки",
        description = "Удаляет расход с указанным ID из вечеринки"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Расход успешно удален"),
        ApiResponse(responseCode = "404", description = "Вечеринка или расход не найдены")
    ])
    @DeleteMapping("/{partyId}/expense")
    @ResponseStatus(HttpStatus.OK)
    fun removePartyExpense(
        @Parameter(description = "ID вечеринки") @PathVariable partyId: Long,
        @Parameter(description = "ID расхода") @RequestParam expenseId: Long
    ): PartyRoomDto

    @Operation(
        summary = "Получить информацию о вечеринке",
        description = "Возвращает полную информацию о вечеринке по ID"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Информация о вечеринке получена"),
        ApiResponse(responseCode = "404", description = "Вечеринка не найдена")
    ])
    @GetMapping("/{partyId}")
    @ResponseStatus(HttpStatus.OK)
    fun getParty(@Parameter(description = "ID вечеринки") @PathVariable partyId: Long): PartyRoomDto?


    companion object {

        const val PARTY_BASE_URL = "/v1/party"
    }
}