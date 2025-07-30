package com.partymakers.shareparty.party.data.mapper

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.partymakers.shareparty.party.data.dto.ExpenseEntity
import com.partymakers.shareparty.party.data.dto.FriendEntity
import org.springframework.stereotype.Component

@Component
internal class EntityMapper(
    private val objectMapper: ObjectMapper
) {
    private val friendListType = object : TypeReference<List<FriendEntity>>() {}
    private val expenseListType = object : TypeReference<List<ExpenseEntity>>() {}


    fun parseFriendList(string: String): List<FriendEntity> {
        return objectMapper.readValue(string, friendListType)
    }

    fun parseExpensesList(string: String): List<ExpenseEntity> {
        return objectMapper.readValue(string, expenseListType)
    }
}
