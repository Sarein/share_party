package com.partymakers.shareparty.data.persistence.party.mapping;

import com.partymakers.shareparty.data.persistence.DomainMapper;
import com.partymakers.shareparty.data.persistence.PersistenceMapper;
import com.partymakers.shareparty.data.persistence.party.PartyRoomEntity;
import com.partymakers.shareparty.domain.entity.PartyRoom;

public class PartyRoomMapping implements DomainMapper<PartyRoom, PartyRoomEntity>, PersistenceMapper<PartyRoomEntity, PartyRoom> {
    @Override
    public PartyRoomEntity toPersistence(PartyRoom domainEntity) {
        return new PartyRoomEntity(domainEntity.getId(), domainEntity.getName());
    }

    @Override
    public PartyRoom toDomain(PartyRoomEntity persistenceEntity) {
        return new PartyRoom(persistenceEntity.getId(), persistenceEntity.getName());
    }
}
