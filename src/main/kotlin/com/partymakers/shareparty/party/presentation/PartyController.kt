package com.partymakers.shareparty.party.presentation

import com.partymakers.common.dto.Content
import com.partymakers.shareparty.party.domain.usecase.*
import com.partymakers.shareparty.party.presentation.dto.ExpenseDto
import com.partymakers.shareparty.party.presentation.dto.InvitedFriendDescriptionDto
import com.partymakers.shareparty.party.presentation.dto.PartyRoomDescriptionDto
import com.partymakers.shareparty.party.presentation.dto.PartyRoomDto
import com.partymakers.shareparty.party.presentation.dto.PartyRoomIdDto
import com.partymakers.shareparty.party.presentation.mapper.ExpenseDtoMapper
import com.partymakers.shareparty.party.presentation.mapper.PartyRoomDtoMapper
import com.partymakers.shareparty.party.presentation.mapper.PartyRoomIdDtoMapper
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(PartyControllerApiV1.PARTY_BASE_URL)
internal class PartyController(
    private val createPartyRoomUseCase: CreatePartyRoomUseCase,
    private val inviteUseCase: InviteFriendUseCase,
    private val kickFriendUseCase: KickFriendUseCase,
    private val addPartyExpenseUseCase: AddPartyExpenseUseCase,
    private val getPartiesListUseCase: GetPartiesUseCase,
    private val removePartyExpenseUseCase: RemovePartyExpenseUseCase,
    private val getPartyUseCase: GetPartyUseCase,
    private val partyRoomIdDtoMapper: PartyRoomIdDtoMapper,
    private val partyRoomDtoMapper: PartyRoomDtoMapper,
    private val expenseDtoMapper: ExpenseDtoMapper,
) : PartyControllerApiV1 {

    override fun createPartyRoom(@RequestBody request: PartyRoomDescriptionDto): PartyRoomIdDto {
        val id = createPartyRoomUseCase(request.name)
        return partyRoomIdDtoMapper.toDto(id)
    }

    override fun getPartiesRoom(): Content<PartyRoomDto> {
        val partiesList = getPartiesListUseCase().map(partyRoomDtoMapper::toDto)
        return Content(partiesList)
    }

    override fun inviteFriendToParty(
        @PathVariable partyId: Long,
        @RequestBody request: InvitedFriendDescriptionDto
    ): PartyRoomDto {
        val partyRoomModel = inviteUseCase(request.nickName, partyId)
        return partyRoomDtoMapper.toDto(partyRoomModel)
    }

    override fun kickFriend(
        @PathVariable partyId: Long,
        @RequestBody request: InvitedFriendDescriptionDto
    ): PartyRoomDto {
        val partyRoomModel = kickFriendUseCase(partyId, request.nickName)
        return partyRoomDtoMapper.toDto(partyRoomModel)
    }

    override fun addPartyExpense(
        @PathVariable partyId: Long,
        @RequestBody request: ExpenseDto
    ): PartyRoomDto {

        val expenseModel = expenseDtoMapper.toModel(request)
        val partyRoomModel = addPartyExpenseUseCase(partyId, expenseModel)
        return partyRoomDtoMapper.toDto(partyRoomModel)
    }

    override fun removePartyExpense(
        @PathVariable partyId: Long,
        @RequestParam expenseId: Long
    ): PartyRoomDto {
        val partyRoomModel = removePartyExpenseUseCase(partyId, expenseId)
        return partyRoomDtoMapper.toDto(partyRoomModel)
    }

    override fun getParty(@PathVariable partyId: Long): PartyRoomDto? {
        val partyRoomModel = getPartyUseCase(partyId)
        return  partyRoomModel?.let(partyRoomDtoMapper::toDto)
    }
}