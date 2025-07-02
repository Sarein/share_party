package com.partymakers.shareparty.data.persistence.friends.impl

import com.partymakers.shareparty.data.persistence.friends.FriendPersistenceRepository
import com.partymakers.shareparty.data.persistence.friends.entity.FriendEntity
import com.partymakers.shareparty.domain.friends.entity.Friend
import com.partymakers.shareparty.domain.friends.port.FriendsRepository
import org.springframework.stereotype.Repository

@Repository
class FriendsRepositoryImpl(
    private val repository: FriendPersistenceRepository
) : FriendsRepository {

    override fun save(friend: Friend): Friend =
        repository.save(FriendEntity.toPersistence(friend)).toDomain()

    override fun findOneById(nickName: String): Friend? =
        repository.findByNickName(nickName)
            ?.toDomain()
}