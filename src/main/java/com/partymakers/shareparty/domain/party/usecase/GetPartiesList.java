package com.partymakers.shareparty.domain.party.usecase;

import com.partymakers.shareparty.domain.party.entity.PartyRoom;

import java.util.List;

public interface GetPartiesList {
    List<PartyRoom> getPartyList();
}
