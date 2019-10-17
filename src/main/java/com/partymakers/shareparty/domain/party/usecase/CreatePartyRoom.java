package com.partymakers.shareparty.domain.party.usecase;

import com.partymakers.shareparty.domain.party.entity.PartyRoomDescription;

public interface CreatePartyRoom {
    long createPartyRoom(PartyRoomDescription partyRoom);
}
