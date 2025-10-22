package com.partymakers.shareparty.party.presentation.mapper

import com.partymakers.shareparty.party.presentation.dto.PartyRoomIdDto
import org.springframework.stereotype.Component

@Component
internal class PartyRoomIdDtoMapper {

    fun toDto(id: Long): PartyRoomIdDto =
        PartyRoomIdDto(
            id = id
        )
}