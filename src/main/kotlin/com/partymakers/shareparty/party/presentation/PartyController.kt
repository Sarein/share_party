package com.partymakers.shareparty.party.presentation

import com.partymakers.shareparty.V1Controller
import com.partymakers.shareparty.party.domain.exception.AlreadyExistException
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.usecase.*
import com.partymakers.shareparty.party.presentation.dto.*
import com.partymakers.shareparty.party.presentation.mapper.ExpenseDtoMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
internal class PartyController(
    private val createPartyRoomUseCase: CreatePartyRoomUseCase,
    private val inviteUseCase: InviteFriendUseCase,
    private val kickFriendUseCase: KickFriendUseCase,
    private val addPartyExpenseUseCase: AddPartyExpenseUseCase,
    private val getPartiesListUseCase: GetPartiesUseCase,
    private val removePartyExpenseUseCase: RemovePartyExpenseUseCase,
    private val getPartyUseCase: GetPartyUseCase,
    private val expenseDtoMapper: ExpenseDtoMapper,
) : V1Controller() {

    @PostMapping("/party")
    fun createPartyRoom(@RequestBody request: PartyRoomDescription): ResponseEntity<Unit> {
        val creationLocation: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createPartyRoomUseCase(request.name))
            .toUri()

        return ResponseEntity
            .created(creationLocation)
            .build()
    }

    @GetMapping("/parties")
    fun getPartiesRoom(): ResponseEntity<*> = try {
        ResponseEntity.ok(Parties(getPartiesListUseCase()))
    } catch (e: Exception) {
        e.printStackTrace()
        ResponseEntity<Unit>(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @PostMapping("/party/{partyId}/friend")
    fun inviteFriendToParty(
        @PathVariable partyId: Long,
        @RequestBody request: InvitedFriendDescription
    ): ResponseEntity<*> = try {
        inviteUseCase(request.nickName, partyId)
        ResponseEntity.ok().build<Unit>()
    } catch (e: AlreadyExistException) {
        ResponseEntity<Unit>(HttpStatus.ALREADY_REPORTED)
    } catch (e: NotFoundException) {
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
    } catch (e: Exception) {
        ResponseEntity<Unit>(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @DeleteMapping("/party/{partyId}/friend")
    fun kickFriend(
        @PathVariable partyId: Long,
        @RequestBody request: InvitedFriendDescription
    ): ResponseEntity<*> = try {
        kickFriendUseCase(partyId, request.nickName)
        ResponseEntity.ok().build<Unit>()
    } catch (e: NotFoundException) {
        ResponseEntity<Unit>(HttpStatus.NOT_FOUND)
    } catch (e: Exception) {
        ResponseEntity<Unit>(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @PostMapping("/party/{partyId}/expense")
    fun addPartyExpense(
        @PathVariable partyId: Long,
        @RequestBody request: ExpenseDto
    ): ResponseEntity<*> = try {

        val dto = ExpenseDto(
            id = null,
            name = request.name,
            cost = request.cost,
            count = request.count
        )
        val expenseModel = expenseDtoMapper.toModel(dto)
        addPartyExpenseUseCase(partyId, expenseModel)
        ResponseEntity.status(HttpStatus.CREATED).build<Unit>()
    } catch (e: NotFoundException) {
        ResponseEntity<Unit>(HttpStatus.NOT_FOUND)
    } catch (e: Exception) {
        ResponseEntity<Unit>(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @DeleteMapping("/party/{partyId}/expense")
    fun removePartyExpense(
        @PathVariable partyId: Long,
        @RequestParam expenseId: Long
    ): ResponseEntity<*> = try {
        removePartyExpenseUseCase(partyId, expenseId)
        ResponseEntity.ok().build<Unit>()
    } catch (e: NotFoundException) {
        ResponseEntity<Unit>(HttpStatus.NOT_FOUND)
    } catch (e: Exception) {
        ResponseEntity<Unit>(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @GetMapping("/party/{partyId}")
    fun getParty(@PathVariable partyId: Long): ResponseEntity<*> = try {
        val partyRoom = getPartyUseCase(partyId)
        if (partyRoom == null) {
            ResponseEntity<Unit>(HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity.ok(partyRoom)
        }
    } catch (e: Exception) {
        ResponseEntity<Unit>(HttpStatus.INTERNAL_SERVER_ERROR)
    }
} 