package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.Expense
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class AddPartyExpenseUseCaseImpl(
    private val repository: PartyRoomRepository
) : AddPartyExpenseUseCase {

    override fun addPartyExpense(expense: Expense, partyId: Long) {
        val partyRoom = repository.findById(partyId)
            .orElseThrow { NotFoundException("Party room with id: $partyId not found") }

        val updatedPartyRoom = partyRoom.copy(expenses = partyRoom.expenses + expense)
        repository.save(updatedPartyRoom)
    }
} 