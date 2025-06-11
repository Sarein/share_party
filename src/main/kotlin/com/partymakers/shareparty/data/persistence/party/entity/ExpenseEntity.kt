package com.partymakers.shareparty.data.persistence.party.entity

import com.partymakers.shareparty.domain.party.entity.Expense
import jakarta.persistence.*

@Entity
@Table(name = "expenses")
data class ExpenseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "expense_id")
    val id: Long = 0,

    @Column(name = "name")
    val name: String = "",

    @Column(name = "cost")
    val cost: Int = 0,

    @Column(name = "count")
    val count: Double = 0.0,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", columnDefinition = "integer")
    val partyRoomEntity: PartyRoomEntity
) {
    companion object {
        fun toPersistence(domainEntity: Expense, partyRoomEntity: PartyRoomEntity): ExpenseEntity {
            return ExpenseEntity(
                name = domainEntity.name,
                cost = domainEntity.cost ?: 0,
                count = domainEntity.count ?: 0.0,
                partyRoomEntity = partyRoomEntity
            )
        }
    }

    fun toDomain(): Expense = Expense(
        name = name,
        cost = cost,
        count = count
    )
} 