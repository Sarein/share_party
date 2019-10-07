package com.partymakers.shareparty.data.persistence.friends.mapping;

import com.partymakers.shareparty.data.persistence.DomainMapper;
import com.partymakers.shareparty.data.persistence.PersistenceMapper;
import com.partymakers.shareparty.data.persistence.friends.FriendEntity;
import com.partymakers.shareparty.domain.entity.Friend;

public class FriendMapping implements DomainMapper<Friend, FriendEntity>, PersistenceMapper<FriendEntity, Friend> {

    @Override
    public FriendEntity toPersistence(Friend domainEntity) {
        return new FriendEntity(domainEntity.getName(), domainEntity.getNickName(), domainEntity.getEMail());
    }

    @Override
    public Friend toDomain(FriendEntity persistenceEntity) {
        return new Friend(persistenceEntity.getName(), persistenceEntity.getNickName(), persistenceEntity.getEMail());
    }
}

