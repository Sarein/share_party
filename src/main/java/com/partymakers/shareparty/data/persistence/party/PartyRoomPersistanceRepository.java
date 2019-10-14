package com.partymakers.shareparty.data.persistence.party;

import com.partymakers.shareparty.data.persistence.party.entity.PartyRoomEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRoomPersistanceRepository extends JpaRepository<PartyRoomEntity, Long> {
}
