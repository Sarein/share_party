package com.partymakers.shareparty.party.data.dto

import jakarta.persistence.*

@Entity
@Table(name = "party_expenses")
data class PartyExpensesEntity(
    @Id
    @GeneratedValue
    private val id: Long = 0,

    @Column(name = "expenses_id")
    private val expenseId: Long = 0,

    @Column(name = "party_room_id")
    private val roomId: Long = 0
)