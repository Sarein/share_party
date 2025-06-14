package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.domain.party.entity.Expense
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository
import com.partymakers.shareparty.domain.party.usecase.RemovePartyExpense
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException

class RemovePartyExpenseImpl(
    private val partyRoomRepository: PartyRoomRepository
) : RemovePartyExpense {

    override fun removePartyExpense(partyId: Long, expense: Expense) {
        val partyRoom = partyRoomRepository.findById(partyId)
            .orElseThrow { NotFoundException("Party room with id: $partyId not found") }

        val updatedPartyRoom = partyRoom.copy(
            expenses = partyRoom.expenses.filterNot { it == expense }
        )
        partyRoomRepository.save(updatedPartyRoom)
    }
} 