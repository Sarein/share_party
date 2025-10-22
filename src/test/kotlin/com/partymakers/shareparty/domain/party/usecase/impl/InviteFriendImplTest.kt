package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.usecase.InviteFriendUseCaseImpl
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class InviteFriendImplTest {

    private val partyRoomRepository: PartyRoomRepository = mock()
    private val useCase = InviteFriendUseCaseImpl(partyRoomRepository)

    @Test
    fun `should invite friend to party room`() {
        // given
        val partyId = 1L
        val nickName = "testFriend"
        val partyRoom = PartyRoom(partyId, "Test Party")

        whenever(partyRoomRepository.addFriend(partyId, nickName)).thenReturn(partyRoom)

        // when
        useCase.invoke(nickName, partyId)

        // then
        verify(partyRoomRepository).addFriend(partyId, nickName)
    }

    @Test
    fun `should throw NotFoundException when party room not found`() {
        // given
        val partyId = 1L
        val nickName = "testFriend"
        whenever(partyRoomRepository.addFriend(partyId, nickName)).thenReturn(null)

        // when/then
        assertThatThrownBy { useCase.invoke(nickName, partyId) }
            .isInstanceOf(NotFoundException::class.java)
    }
} 