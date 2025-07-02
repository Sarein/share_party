package com.partymakers.shareparty.party.data.repository

import com.partymakers.shareparty.party.data.dto.PartyRoomEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PartyRoomPersistenceRepository : JpaRepository<PartyRoomEntity, Long>