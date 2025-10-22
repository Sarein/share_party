package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import org.springframework.stereotype.Service

@Service
internal class RemovePartyExpenseUseCaseImpl(
    private val partyRoomRepository: PartyRoomRepository
) : RemovePartyExpenseUseCase {

    override fun invoke(partyId: Long, expenseId: Long): PartyRoom  {
        if(!partyRoomRepository.existsById(partyId)) {
            throw NotFoundException("Requested room is not exist")
        }

        partyRoomRepository.deleteExpense(partyId, expenseId)

        return partyRoomRepository.findById(partyId) ?: throw NotFoundException("Requested room is not exist")

    }

} 