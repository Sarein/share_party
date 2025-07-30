package com.partymakers.shareparty.party.data.mapper

import com.partymakers.shareparty.party.data.dto.ExpenseEntity
import com.partymakers.shareparty.party.domain.entity.Expense
import org.springframework.stereotype.Component

@Component
internal class ExpenseMapper {

    fun toModel(dto: ExpenseEntity): Expense = Expense(
        id = dto.id, name = dto.name, cost = dto.cost, count = dto.count,
    )
}