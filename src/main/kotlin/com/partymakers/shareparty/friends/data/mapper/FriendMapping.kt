package com.partymakers.shareparty.friends.data.mapper

import com.partymakers.shareparty.friends.data.dto.FriendEntity
import com.partymakers.shareparty.friends.data.mapper.DomainMapper
import com.partymakers.shareparty.friends.data.mapper.PersistenceMapper
import com.partymakers.shareparty.friends.domain.entity.Friend

class FriendMapping : DomainMapper<Friend, FriendEntity>, PersistenceMapper<FriendEntity, Friend> {

    override fun toPersistence(domainEntity: Friend): FriendEntity =
        FriendEntity(domainEntity.name, domainEntity.nickName, domainEntity.eMail)

    override fun toDomain(persistenceEntity: FriendEntity): Friend =
        Friend(
            persistenceEntity.name.orEmpty(),
            persistenceEntity.nickName.orEmpty(),
            persistenceEntity.eMail.orEmpty()
        )
}