package com.partymakers.shareparty.data.persistence.friends.entity

import com.partymakers.shareparty.data.persistence.helper.DomainMapper
import com.partymakers.shareparty.data.persistence.helper.PersistenceMapper
import com.partymakers.shareparty.domain.friends.entity.Friend

class FriendMapping : DomainMapper<Friend, FriendEntity>, PersistenceMapper<FriendEntity, Friend> {

    override fun toPersistence(domainEntity: Friend): FriendEntity =
        FriendEntity(domainEntity.name, domainEntity.nickName, domainEntity.eMail)

    override fun toDomain(persistenceEntity: FriendEntity): Friend =
        Friend(persistenceEntity.name.orEmpty(),
               persistenceEntity.nickName.orEmpty(),
               persistenceEntity.eMail.orEmpty())
}