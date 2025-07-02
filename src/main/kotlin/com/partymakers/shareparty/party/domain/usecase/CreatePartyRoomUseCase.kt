package com.partymakers.shareparty.party.domain.usecase

interface CreatePartyRoomUseCase {
    fun createPartyRoom(description: String): Long
} 