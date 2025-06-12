package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.domain.party.entity.Expense
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository
import com.partymakers.shareparty.domain.party.usecase.AddPartyExpense
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException

class AddPartyExpenseImpl(
    private val repository: PartyRoomRepository
) : AddPartyExpense {

    override fun addPartyExpense(expense: Expense, partyId: Long) {
        val partyRoom = repository.findById(partyId)
            .orElseThrow { NotFoundException("Party room with id: $partyId not found") }

        val updatedPartyRoom = partyRoom.copy(expenses = partyRoom.expenses + expense)
        repository.save(updatedPartyRoom)
    }
} 