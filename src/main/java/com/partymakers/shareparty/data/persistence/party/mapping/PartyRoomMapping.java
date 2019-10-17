package com.partymakers.shareparty.data.persistence.party.mapping;

import com.partymakers.shareparty.data.persistence.DomainMapper;
import com.partymakers.shareparty.data.persistence.PersistenceMapper;
import com.partymakers.shareparty.data.persistence.party.entity.PartyRoomEntity;
import com.partymakers.shareparty.domain.party.entity.PartyRoom;
import com.partymakers.shareparty.domain.party.entity.PartyRoomDescription;

public class PartyRoomMapping implements DomainMapper<PartyRoomDescription, PartyRoomEntity>, PersistenceMapper<PartyRoomEntity, PartyRoomDescription> {
    @Override
    public PartyRoomEntity toPersistence(PartyRoomDescription domainEntity) {
        return new PartyRoomEntity(domainEntity.getId(), domainEntity.getName());
    }

    @Override
    public PartyRoomDescription toDomain(PartyRoomEntity persistenceEntity) {
        return new PartyRoomDescription(persistenceEntity.getId(), persistenceEntity.getName());
    }
}
