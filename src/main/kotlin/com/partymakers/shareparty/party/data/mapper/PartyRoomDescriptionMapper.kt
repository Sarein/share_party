package com.partymakers.shareparty.party.data.mapper

import com.partymakers.shareparty.party.data.dto.PartyRoomDescriptionEntity
import com.partymakers.shareparty.party.domain.entity.PartyRoomDescription
import org.springframework.stereotype.Component

@Component
internal class PartyRoomDescriptionMapper {

    fun toEntity(dto: PartyRoomDescriptionEntity): PartyRoomDescription =
        PartyRoomDescription(
            id = dto.id,
            name = dto.name,
        )
}