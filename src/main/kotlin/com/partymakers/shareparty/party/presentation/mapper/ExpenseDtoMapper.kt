package com.partymakers.shareparty.party.presentation.mapper

import com.partymakers.shareparty.party.domain.entity.Expense
import com.partymakers.shareparty.party.presentation.dto.ExpenseDto
import org.springframework.stereotype.Component

@Component
internal class ExpenseDtoMapper {

    fun toModel(dto: ExpenseDto): Expense =
        Expense(
            id = dto.id?.toLongOrNull(),
            name = dto.name,
            cost = dto.cost,
            count = dto.count
        )
}