package com.partymakers.shareparty.data.persistence.party.impl

import com.partymakers.shareparty.data.persistence.party.PartyRoomPersistenceRepository
import com.partymakers.shareparty.data.persistence.party.entity.PartyRoomEntity
import com.partymakers.shareparty.domain.party.entity.PartyRoom
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class PartyRoomRepositoryImpl(
    private val repository: PartyRoomPersistenceRepository
) : PartyRoomRepository {

    override fun save(partyRoom: PartyRoom): PartyRoom =
        repository.save(PartyRoomEntity.toPersistence(partyRoom)).toDomain()

    override fun findById(id: Long): Optional<PartyRoom> =
        repository.findById(id).map { it.toDomain() }

    override fun findAll(): Iterable<PartyRoom> =
        repository.findAll().map { it.toDomain() }

    override fun deleteAll() {
        repository.deleteAll()
    }
} 