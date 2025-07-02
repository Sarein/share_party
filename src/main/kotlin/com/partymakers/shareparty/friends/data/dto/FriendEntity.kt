package com.partymakers.shareparty.friends.data.dto

import com.partymakers.shareparty.party.data.dto.PartyRoomEntity
import com.partymakers.shareparty.friends.domain.entity.Friend
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.util.HashSet
import java.util.Objects

@Entity
@Table(name = "friends")
class FriendEntity() {

    @Id
    @Column(name = "nick_name")
    var nickName: String? = null

    @Column(name = "name")
    var name: String? = null

    @Column(name = "e_mail")
    var eMail: String? = null

    @ManyToMany(mappedBy = "invitedFriends", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var partyRooms: MutableSet<PartyRoomEntity> = HashSet()

    constructor(name: String, nickName: String, eMail: String) : this() {
        this.name = name
        this.nickName = nickName
        this.eMail = eMail
    }

    fun toDomain(): Friend =
        Friend(name.orEmpty(), nickName.orEmpty(), eMail.orEmpty())

    companion object {
        fun toPersistence(domainEntity: Friend): FriendEntity =
            FriendEntity(domainEntity.name, domainEntity.nickName, domainEntity.eMail)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FriendEntity) return false
        return nickName == other.nickName
            && name == other.name
            && eMail == other.eMail
    }

    override fun hashCode(): Int =
        Objects.hash(nickName, name, eMail)
}