package com.partymakers.shareparty.party.presentation

import com.partymakers.shareparty.V1Controller
import com.partymakers.shareparty.party.domain.entity.Expense as DomainExpense
import com.partymakers.shareparty.party.domain.exception.AlreadyExistException
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.usecase.AddPartyExpenseUseCase
import com.partymakers.shareparty.party.domain.usecase.CreatePartyRoomUseCase
import com.partymakers.shareparty.party.domain.usecase.GetPartiesUseCase
import com.partymakers.shareparty.party.domain.usecase.GetPartyUseCase
import com.partymakers.shareparty.party.domain.usecase.GetPartyExpensesUseCase
import com.partymakers.shareparty.party.domain.usecase.GetPartyFriendsUseCase
import com.partymakers.shareparty.friends.domain.usecase.InviteFriendUseCase
import com.partymakers.shareparty.friends.domain.usecase.KickFriendUseCase
import com.partymakers.shareparty.party.domain.usecase.RemovePartyExpenseUseCase
import com.partymakers.shareparty.party.presentation.dto.Expense
import com.partymakers.shareparty.party.presentation.dto.FullPartyInfo
import com.partymakers.shareparty.party.presentation.dto.InvitedFriendDescription
import com.partymakers.shareparty.party.presentation.dto.Parties
import com.partymakers.shareparty.party.presentation.dto.PartyExpenses
import com.partymakers.shareparty.party.presentation.dto.PartyFriends
import com.partymakers.shareparty.party.presentation.dto.PartyRoomDescription
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
class PartyController(
    private val createPartyRoomUseCase: CreatePartyRoomUseCase,
    private val inviteUseCase: InviteFriendUseCase,
    private val kickFriendUseCase: KickFriendUseCase,
    private val partyExpenseUseCase: AddPartyExpenseUseCase,
    private val getPartiesListUseCase: GetPartiesUseCase,
    private val getPartyFriendsUseCase: GetPartyFriendsUseCase,
    private val getPartyExpensesUseCase: GetPartyExpensesUseCase,
    private val removePartyExpenseUseCase: RemovePartyExpenseUseCase,
    private val getPartyUseCase: GetPartyUseCase
) : V1Controller() {

    @PostMapping("/party")
    fun createPartyRoom(@RequestBody request: PartyRoomDescription): ResponseEntity<Unit> {
        val creationLocation: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createPartyRoomUseCase.createPartyRoom(request.name))
            .toUri()

        return ResponseEntity
            .created(creationLocation)
            .build()
    }

    @GetMapping("/party")
    fun getPartiesRoom(): ResponseEntity<*> = try {
        ResponseEntity.ok(Parties(getPartiesListUseCase.getParties()))
    } catch (e: Exception) {
        ResponseEntity<Unit>(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @PostMapping("/party/{partyId}/friend")
    fun inviteFriendToParty(
        @PathVariable partyId: Long,
        @RequestBody request: InvitedFriendDescription
    ): ResponseEntity<*> = try {
        inviteUseCase.inviteFriend(request.nickName, partyId)
        ResponseEntity.ok().build<Unit>()
    } catch (e: AlreadyExistException) {
        ResponseEntity<Unit>(HttpStatus.ALREADY_REPORTED)
    } catch (e: NotFoundException) {
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
    } catch (e: Exception) {
        ResponseEntity<Unit>(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @GetMapping("/party/{partyId}/friend")
    fun getPartyFriend(@PathVariable partyId: Long): ResponseEntity<*> = try {
        ResponseEntity.ok(PartyFriends(getPartyFriendsUseCase.getPartyFriends(partyId)))
    } catch (e: NotFoundException) {
        ResponseEntity<Unit>(HttpStatus.NOT_FOUND)
    } catch (e: Exception) {
        ResponseEntity<Unit>(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @DeleteMapping("/party/{partyId}/friend")
    fun kickFriend(
        @PathVariable partyId: Long,
        @RequestBody request: InvitedFriendDescription
    ): ResponseEntity<*> = try {
        kickFriendUseCase.kickFriend(request.nickName, partyId)
        ResponseEntity.ok().build<Unit>()
    } catch (e: NotFoundException) {
        ResponseEntity<Unit>(HttpStatus.NOT_FOUND)
    } catch (e: Exception) {
        ResponseEntity<Unit>(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @PostMapping("/party/{partyId}/expense")
    fun addPartyExpense(
        @PathVariable partyId: Long,
        @RequestBody request: Expense
    ): ResponseEntity<*> = try {
        partyExpenseUseCase.addPartyExpense(
            DomainExpense(
                name = request.name,
                cost = request.cost,
                count = request.count
            ),
            partyId
        )
        ResponseEntity.status(HttpStatus.CREATED).build<Unit>()
    } catch (e: NotFoundException) {
        ResponseEntity<Unit>(HttpStatus.NOT_FOUND)
    } catch (e: Exception) {
        ResponseEntity<Unit>(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @GetMapping("/party/{partyId}/expense")
    fun getPartyExpenses(@PathVariable partyId: Long): ResponseEntity<*> = try {
        ResponseEntity.ok(PartyExpenses(getPartyExpensesUseCase.getPartyExpenses(partyId)))
    } catch (e: NotFoundException) {
        ResponseEntity<Unit>(HttpStatus.NOT_FOUND)
    } catch (e: Exception) {
        ResponseEntity<Unit>(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @DeleteMapping("/party/{partyId}/expense")
    fun removePartyExpense(
        @PathVariable partyId: Long,
        @RequestBody request: Expense
    ): ResponseEntity<*> = try {
        removePartyExpenseUseCase.removePartyExpense(
            partyId,
            DomainExpense(
                name = request.name,
                cost = request.cost,
                count = request.count
            )
        )
        ResponseEntity.ok().build<Unit>()
    } catch (e: NotFoundException) {
        ResponseEntity<Unit>(HttpStatus.NOT_FOUND)
    } catch (e: Exception) {
        ResponseEntity<Unit>(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @GetMapping("/party/{partyId}")
    fun getParty(@PathVariable partyId: Long): ResponseEntity<*> = try {
        ResponseEntity.ok(FullPartyInfo(getPartyUseCase.getParty(partyId)))
    } catch (e: NotFoundException) {
        ResponseEntity<Unit>(HttpStatus.NOT_FOUND)
    } catch (e: Exception) {
        ResponseEntity<Unit>(HttpStatus.INTERNAL_SERVER_ERROR)
    }
} 