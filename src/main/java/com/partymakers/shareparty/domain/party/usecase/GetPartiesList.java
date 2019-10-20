package com.partymakers.shareparty.domain.party.usecase;

import com.partymakers.shareparty.domain.party.entity.PartyRoomDescription;

import java.util.List;
import java.util.Set;

public interface GetPartiesList {
    Set<PartyRoomDescription> getPartyList();
}
