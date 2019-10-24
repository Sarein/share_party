package com.partymakers.shareparty.domain.party.port;

import com.partymakers.shareparty.domain.party.entity.PartyRoom;

public interface PartyRoomRepository {
    PartyRoom save(PartyRoom partyRoom);
    //TODO: change return value to optional
    PartyRoom findById(long id);
    Iterable<PartyRoom> findAll();
}
