package com.partymakers.shareparty.domain.party.usecase;

import com.partymakers.shareparty.domain.party.entity.PartyRoom;

public interface GetParty {
    PartyRoom getParty(Long partyId);
}
