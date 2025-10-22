package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.entity.PartyRoomDescription
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.usecase.GetPartiesUseCaseImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetPartiesListImplTest {

    private val repository: PartyRoomRepository = mock()
    private val useCase = GetPartiesUseCaseImpl(repository)

    @Test
    fun `should return empty set when no party rooms exist`() {
        // given
        whenever(repository.findAll()).thenReturn(emptyList())

        // when
        val result = useCase()

        // then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should return set of party room descriptions`() {
        // given
        val partyRooms = listOf(
            PartyRoom(1L, "Party 1"),
            PartyRoom(2L, "Party 2"),
            PartyRoom(3L, "Party 3")
        )
        whenever(repository.findAll()).thenReturn(partyRooms)

        // when
        val result = useCase()

        // then
        assertThat(result).hasSize(3)
        assertThat(result).containsExactlyInAnyOrder(
            PartyRoom(1L, "Party 1"),
            PartyRoom(2L, "Party 2"),
            PartyRoom(3L, "Party 3")
        )
    }
} 