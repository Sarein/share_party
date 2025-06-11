package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.domain.party.entity.PartyRoom
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CreatePartyRoomImplTest {
    private val repository: PartyRoomRepository = mock()

    private val useCase: CreatePartyRoomImpl = CreatePartyRoomImpl(repository)

    private val partyRoomCaptor = argumentCaptor<PartyRoom>()

    @Test
    fun `should create party room and return its id`() {
        // given
        val partyName = "Test Party"
        val savedPartyRoom = PartyRoom(1L, partyName)
        whenever(repository.save(any())).thenReturn(savedPartyRoom)

        // when
        val result = useCase.createPartyRoom(partyName)

        // then
        assertThat(result).isEqualTo(1L)
        verify(repository).save(any())
    }

    @Test
    fun `should create party room with correct name`() {
        // given
        val partyName = "Test Party"
        val savedPartyRoom = PartyRoom(1L, partyName)
        whenever(repository.save(any())).thenReturn(savedPartyRoom)

        // when
        useCase.createPartyRoom(partyName)

        // then
        verify(repository).save(partyRoomCaptor.capture())
        assertThat(partyRoomCaptor.lastValue.description.name).isEqualTo(partyName)
    }
} 