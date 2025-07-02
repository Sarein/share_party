package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.Expense
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class RemovePartyExpenseImpl(
    private val partyRoomRepository: PartyRoomRepository
) : RemovePartyExpenseUseCase {

    override fun removePartyExpense(partyId: Long, expense: Expense) {
        val partyRoom = partyRoomRepository.findById(partyId)
            .orElseThrow { NotFoundException("Party room with id: $partyId not found") }

        val updatedPartyRoom = partyRoom.copy(
            expenses = partyRoom.expenses.filterNot { it == expense }
        )
        partyRoomRepository.save(updatedPartyRoom)
    }
} 