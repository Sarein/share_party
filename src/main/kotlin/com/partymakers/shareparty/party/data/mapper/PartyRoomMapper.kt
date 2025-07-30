package com.partymakers.shareparty.party.data.mapper

import com.partymakers.shareparty.party.data.dto.PartyRoomEntity
import com.partymakers.shareparty.party.domain.entity.PartyRoom
import org.springframework.stereotype.Component

@Component
internal class PartyRoomMapper(
    private val descriptionMapper: PartyRoomDescriptionMapper,
    private val friendMapper: FriendMapper,
    private val expenseMapper: ExpenseMapper,
) {

    fun toEntity(dto: PartyRoomEntity): PartyRoom =
        PartyRoom(
            description = descriptionMapper.toEntity(dto.description),
            friends = dto.friends.map(friendMapper::toEntity).toSet(),
            expenses = dto.expenses.map(expenseMapper::toModel),
        )
}