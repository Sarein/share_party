package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.usecase.CreatePartyRoomUseCaseImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CreatePartyRoomImplTest {
    private val repository: PartyRoomRepository = mock()

    private val useCase: CreatePartyRoomUseCaseImpl = CreatePartyRoomUseCaseImpl(repository)


    @Test
    fun `should create party room and return its id`() {
        // given
        val partyName = "Test Party"
        whenever(repository.create(partyName)).thenReturn(1)

        // when
        val result = useCase.invoke(partyName)

        // then
        assertThat(result).isEqualTo(1)
        verify(repository).create(partyName)
    }

    @Test
    fun `should create party room with correct name`() {
        // given
        val partyName = "Test Party"
        whenever(repository.create(partyName)).thenReturn(1)

        // when
        useCase.invoke(partyName)

        // then
        verify(repository).create(partyName)
    }
} 