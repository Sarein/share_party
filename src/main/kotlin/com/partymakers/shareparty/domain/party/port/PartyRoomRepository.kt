package com.partymakers.shareparty.domain.party.port

import com.partymakers.shareparty.domain.party.entity.PartyRoom
import java.util.Optional

interface PartyRoomRepository {
    fun save(partyRoom: PartyRoom): PartyRoom
    fun findById(id: Long): Optional<PartyRoom>
    fun findAll(): Iterable<PartyRoom>
} 