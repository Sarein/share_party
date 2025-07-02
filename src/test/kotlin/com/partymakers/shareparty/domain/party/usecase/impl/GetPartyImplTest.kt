package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.usecase.GetPartyUseCaseImpl
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.Optional

class GetPartyImplTest {

    private val repository: PartyRoomRepository = mock()
    private val useCase = GetPartyUseCaseImpl(repository)

    @Test
    fun `should return party room when it exists`() {
        // given
        val partyId = 1L
        val partyRoom = PartyRoom(partyId, "Test Party")
        whenever(repository.findById(partyId)).thenReturn(Optional.of(partyRoom))

        // when
        val result = useCase.getParty(partyId)

        // then
        assertThat(result).isEqualTo(partyRoom)
    }

    @Test
    fun `should throw NotFoundException when party room does not exist`() {
        // given
        val partyId = 1L
        whenever(repository.findById(partyId)).thenReturn(Optional.empty())

        // when/then
        assertThatThrownBy { useCase.getParty(partyId) }
            .isInstanceOf(NotFoundException::class.java)
    }
} 