package com.partymakers.shareparty.friends.data.repository

import com.partymakers.shareparty.friends.data.dto.FriendEntity
import com.partymakers.shareparty.friends.domain.entity.Friend
import com.partymakers.shareparty.friends.domain.repository.FriendsRepository
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