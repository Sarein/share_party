package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.domain.party.entity.Expense
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository
import com.partymakers.shareparty.domain.party.usecase.GetPartyExpenses
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class GetPartyExpensesImpl(
    private val repository: PartyRoomRepository
) : GetPartyExpenses {

    override fun getPartyExpenses(partyId: Long): List<Expense> =
        repository.findById(partyId)
            .map { it.expenses }
            .orElseThrow { NotFoundException("Party room with id: $partyId not found") }
} 