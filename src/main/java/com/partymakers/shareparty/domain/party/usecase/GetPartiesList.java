package com.partymakers.shareparty.domain.party.usecase;

import com.partymakers.shareparty.domain.party.entity.PartyRoomDescription;

import java.util.List;

public interface GetPartiesList {
    List<PartyRoomDescription> getPartyList();
}
