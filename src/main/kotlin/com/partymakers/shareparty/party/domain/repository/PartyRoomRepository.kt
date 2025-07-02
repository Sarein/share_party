package com.partymakers.shareparty.party.domain.repository

import com.partymakers.shareparty.party.domain.entity.PartyRoom
import java.util.Optional

interface PartyRoomRepository {
    fun save(partyRoom: PartyRoom): PartyRoom
    fun findById(id: Long): Optional<PartyRoom>
    fun findAll(): Iterable<PartyRoom>
    fun deleteAll()
}