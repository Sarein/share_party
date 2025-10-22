package com.partymakers.shareparty.party.presentation

import com.partymakers.common.dto.Content
import com.partymakers.shareparty.party.presentation.dto.ExpenseDto
import com.partymakers.shareparty.party.presentation.dto.InvitedFriendDescriptionDto
import com.partymakers.shareparty.party.presentation.dto.PartyRoomDescriptionDto
import com.partymakers.shareparty.party.presentation.dto.PartyRoomDto
import com.partymakers.shareparty.party.presentation.dto.PartyRoomIdDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.PostMapping


internal interface PartyControllerApiV1 {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPartyRoom(@RequestBody request: PartyRoomDescriptionDto): PartyRoomIdDto

    @GetMapping("/parties")
    @ResponseStatus(HttpStatus.OK)
    fun getPartiesRoom(): Content<PartyRoomDto>

    @PostMapping("/{partyId}/friend")
    @ResponseStatus(HttpStatus.OK)
    fun inviteFriendToParty(
        @PathVariable partyId: Long,
        @RequestBody request: InvitedFriendDescriptionDto
    ): PartyRoomDto

    @DeleteMapping("/{partyId}/friend")
    @ResponseStatus(HttpStatus.OK)
    fun kickFriend(
        @PathVariable partyId: Long,
        @RequestParam nickName: String,
    ): PartyRoomDto?

    @PostMapping("/{partyId}/expense")
    @ResponseStatus(HttpStatus.CREATED)
    fun addPartyExpense(
        @PathVariable partyId: Long,
        @RequestBody request: ExpenseDto
    ): PartyRoomDto

    @DeleteMapping("/{partyId}/expense")
    @ResponseStatus(HttpStatus.OK)
    fun removePartyExpense(
        @PathVariable partyId: Long,
        @RequestParam expenseId: Long
    ): PartyRoomDto

    @GetMapping("/{partyId}")
    @ResponseStatus(HttpStatus.OK)
    fun getParty(@PathVariable partyId: Long): PartyRoomDto?


    companion object {
        const val PARTY_BASE_URL = "/v1/party"
    }
}