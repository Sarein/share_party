package com.partymakers.shareparty.party.domain.repository

import com.partymakers.shareparty.party.domain.entity.Expense
import com.partymakers.shareparty.party.domain.entity.PartyRoom

internal interface PartyRoomRepository {
    fun create(partyName: String): Long
    fun addFriend(roomId: Long, friendNickName: String): PartyRoom?
    fun deleteFriend(roomId: Long, friendNickName: String): PartyRoom?
    fun addExpense(roomId: Long, expense: Expense): PartyRoom?
    fun deleteExpense(roomId: Long, expenseId: Long): PartyRoom?
    fun findById(roomId: Long): PartyRoom?
    fun findAll(): List<PartyRoom>
    fun deleteById(roomId: Long)
    fun deleteAll()
    fun addFriends(roomId: Long, friendNickNames: List<String>): PartyRoom?
}