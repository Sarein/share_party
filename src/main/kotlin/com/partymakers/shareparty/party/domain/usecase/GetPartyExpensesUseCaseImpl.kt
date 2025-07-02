package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.entity.Expense
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class GetPartyExpensesUseCaseImpl(
    private val repository: PartyRoomRepository
) : GetPartyExpensesUseCase {

    override fun getPartyExpenses(partyId: Long): List<Expense> =
        repository.findById(partyId)
            .map { it.expenses }
            .orElseThrow { NotFoundException("Party room with id: $partyId not found") }
} 