package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.Expense
import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import org.springframework.stereotype.Service

@Service
internal class AddPartyExpenseUseCaseImpl(
    private val repository: PartyRoomRepository
) : AddPartyExpenseUseCase {

    override fun invoke(roomId: Long, expense: Expense): PartyRoom {
        if(!repository.existsById(roomId)) {
            throw NotFoundException("Requested room is not exist")
        }

        repository.addExpense(roomId, expense)

        return repository.findById(roomId) ?: throw NotFoundException("Requested room is not exist")
    }
}