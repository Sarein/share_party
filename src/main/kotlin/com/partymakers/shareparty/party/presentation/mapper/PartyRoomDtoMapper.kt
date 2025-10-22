package com.partymakers.shareparty.party.presentation.mapper

import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.presentation.dto.PartyRoomDto
import org.springframework.stereotype.Component

@Component
internal class PartyRoomDtoMapper {

    fun toDto(partyRoom: PartyRoom): PartyRoomDto =
        PartyRoomDto(
            id = partyRoom.description.id,
            name = partyRoom.description.name,
            friends = partyRoom.friends,
            expenses = partyRoom.expenses
        )
}