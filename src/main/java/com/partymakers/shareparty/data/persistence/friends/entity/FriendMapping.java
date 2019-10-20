package com.partymakers.shareparty.data.persistence.friends.entity;

import com.partymakers.shareparty.data.persistence.helper.DomainMapper;
import com.partymakers.shareparty.data.persistence.helper.PersistenceMapper;
import com.partymakers.shareparty.domain.friends.entity.Friend;

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

