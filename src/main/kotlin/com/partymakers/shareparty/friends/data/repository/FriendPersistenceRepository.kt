package com.partymakers.shareparty.friends.data.repository

import com.partymakers.shareparty.friends.data.dto.FriendEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FriendPersistenceRepository : JpaRepository<FriendEntity, String> {

    fun findByNickName(nickName: String): FriendEntity?
}