package com.partymakers.shareparty.domain.party.usecase

interface CreatePartyRoom {
    fun createPartyRoom(description: String): Long
} 