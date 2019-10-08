package com.partymakers.shareparty.domain.usecases.party;

import com.partymakers.shareparty.domain.entity.PartyRoom;

public interface CreatePartyRoom {
    long createPartyRoom(PartyRoom partyRoom);
}
