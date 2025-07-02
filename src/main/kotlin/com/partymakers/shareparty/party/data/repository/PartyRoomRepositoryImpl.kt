package com.partymakers.shareparty.party.data.repository

import com.partymakers.shareparty.party.data.dto.PartyRoomEntity
import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class PartyRoomRepositoryImpl(
    private val repository: PartyRoomPersistenceRepository
) : PartyRoomRepository {

    override fun save(partyRoom: PartyRoom): PartyRoom =
        repository.save(PartyRoomEntity.Companion.toPersistence(partyRoom)).toDomain()

    override fun findById(id: Long): Optional<PartyRoom> =
        repository.findById(id).map { it.toDomain() }

    override fun findAll(): Iterable<PartyRoom> =
        repository.findAll().map { it.toDomain() }

    override fun deleteAll() {
        repository.deleteAll()
    }
}