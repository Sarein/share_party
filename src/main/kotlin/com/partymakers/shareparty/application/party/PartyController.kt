package com.partymakers.shareparty.application.party

import com.partymakers.shareparty.application.V1Controller
import com.partymakers.shareparty.application.party.dto.*
import com.partymakers.shareparty.domain.party.entity.Expense as DomainExpense
import com.partymakers.shareparty.domain.party.usecase.*
import com.partymakers.shareparty.domain.party.usecase.exception.AlreadyExistException
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
class PartyController(
    private val createPartyRoom: CreatePartyRoom,
    private val inviteUseCase: InviteFriend,
    private val kickUseCase: KickFriend,
    private val partyExpense: AddPartyExpense,
    private val getPartiesList: GetPartiesList,
    private val getPartyFriends: GetPartyFriends,
    private val getPartyExpenses: GetPartyExpenses,
    private val removePartyExpense: RemovePartyExpense,
    private val getParty: GetParty
) : V1Controller() {

    @PostMapping("/party")
    fun createPartyRoom(@RequestBody request: PartyRoomDescription): ResponseEntity<Unit> {
        val creationLocation: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createPartyRoom.createPartyRoom(request.name))
            .toUri()

        return ResponseEntity
            .created(creationLocation)
            .build()
    }

    @GetMapping("/party")
    fun getPartiesRoom(): ResponseEntity<*> = try {
        ResponseEntity.ok(Parties(getPartiesList.getPartyList()))
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
        ResponseEntity.ok(PartyFriends(getPartyFriends.getPartyFriends(partyId)))
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
        kickUseCase.kickFriend(request.nickName, partyId)
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
        partyExpense.addPartyExpense(
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
        ResponseEntity.ok(PartyExpenses(getPartyExpenses.getPartyExpenses(partyId)))
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
        removePartyExpense.removePartyExpense(
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
        ResponseEntity.ok(FullPartyInfo(getParty.getParty(partyId)))
    } catch (e: NotFoundException) {
        ResponseEntity<Unit>(HttpStatus.NOT_FOUND)
    } catch (e: Exception) {
        ResponseEntity<Unit>(HttpStatus.INTERNAL_SERVER_ERROR)
    }
} 