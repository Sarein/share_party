package com.partymakers.shareparty.data.persistence.party.entity

import com.partymakers.shareparty.data.persistence.friends.entity.FriendEntity
import com.partymakers.shareparty.domain.party.entity.PartyRoom
import jakarta.persistence.*

@Entity
@Table(name = "party_room")
data class PartyRoomEntity(
    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "name")
    val name: String = "",

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], targetEntity = FriendEntity::class)
    @JoinTable(
        name = "partyroom_friends",
        joinColumns = [JoinColumn(name = "partyroom_id")],
        inverseJoinColumns = [JoinColumn(name = "friend_nick_name")]
    )
    val invitedFriends: Set<FriendEntity> = setOf(),
) {
    @OneToMany(
        mappedBy = "partyRoomEntity",
        fetch = FetchType.EAGER,
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        targetEntity = ExpenseEntity::class
    )
    var expenses: List<ExpenseEntity> = listOf()

    companion object {
        fun toPersistence(domainEntity: PartyRoom): PartyRoomEntity {
            val roomEntity = PartyRoomEntity(
                id = domainEntity.description.id,
                name = domainEntity.description.name,
                invitedFriends = domainEntity.friends.map { FriendEntity.toPersistence(it) }.toSet(),
            )

            roomEntity.expenses =
                domainEntity.expenses.map { expense ->
                    ExpenseEntity.toPersistence(expense, roomEntity)
                }

            return roomEntity
        }
    }

    fun toDomain(): PartyRoom = PartyRoom(
        id = id,
        name = name,
        friends = invitedFriends.map { it.toDomain() }.toSet(),
        expenses = expenses.map { it.toDomain() }
    )
} 