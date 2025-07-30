package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import org.springframework.stereotype.Service

@Service
internal class RemovePartyExpenseUseCaseImpl(
    private val partyRoomRepository: PartyRoomRepository
) : RemovePartyExpenseUseCase {

    override fun invoke(partyId: Long, expenseId: Long): PartyRoom =
        partyRoomRepository.deleteExpense(partyId, expenseId)
            ?: throw NotFoundException("Requested room is not exist")
} 