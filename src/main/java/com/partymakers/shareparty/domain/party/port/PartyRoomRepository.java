package com.partymakers.shareparty.domain.party.port;

import com.partymakers.shareparty.domain.party.entity.PartyRoom;

public interface PartyRoomRepository {
    PartyRoom save(PartyRoom partyRoom);
    PartyRoom findById(long id);
    Iterable<PartyRoom> findAll();
}
