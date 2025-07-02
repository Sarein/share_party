package com.partymakers.shareparty.data.persistence.friends.entity

import com.partymakers.shareparty.data.persistence.party.entity.PartyRoomEntity
import com.partymakers.shareparty.domain.friends.entity.Friend
import jakarta.persistence.*
import java.util.*

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