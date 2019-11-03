package com.partymakers.shareparty.domain.party.port;

import com.partymakers.shareparty.domain.party.entity.PartyRoom;

import java.util.Optional;

public interface PartyRoomRepository {
    PartyRoom save(PartyRoom partyRoom);
    Optional<PartyRoom> findById(long id);
    Iterable<PartyRoom> findAll();
}
