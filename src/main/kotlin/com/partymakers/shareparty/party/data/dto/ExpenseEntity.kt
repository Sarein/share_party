package com.partymakers.shareparty.party.data.dto

import com.partymakers.shareparty.party.domain.entity.Expense
import jakarta.persistence.*

@Entity
@Table(name = "expenses")
data class ExpenseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    val id: Long = 0,

    @Column(name = "name")
    val name: String = "",

    @Column(name = "cost")
    val cost: Int = 0,

    @Column(name = "count")
    val count: Double = 0.0,
) {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", columnDefinition = "integer")
    lateinit var partyRoomEntity: PartyRoomEntity

    companion object {
        fun toPersistence(domainEntity: Expense, partyRoomEntity: PartyRoomEntity): ExpenseEntity {
            val expense = ExpenseEntity(
                name = domainEntity.name,
                cost = domainEntity.cost ?: 0,
                count = domainEntity.count ?: 0.0,
            )
            expense.partyRoomEntity = partyRoomEntity
            return expense
        }
    }

    fun toDomain(): Expense = Expense(
        name = name,
        cost = cost,
        count = count
    )
} 