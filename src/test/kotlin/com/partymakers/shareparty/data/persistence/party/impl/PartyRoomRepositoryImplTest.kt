package com.partymakers.shareparty.data.persistence.party.impl

import com.partymakers.shareparty.party.data.repository.PartyRoomPersistenceRepository
import com.partymakers.shareparty.party.data.dto.PartyRoomEntity
import com.partymakers.shareparty.party.data.repository.PartyRoomRepositoryImpl
import com.partymakers.shareparty.party.domain.entity.PartyRoom
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

class PartyRoomRepositoryImplTest {

    private val persistenceRepository: PartyRoomPersistenceRepository = mock()

    private val repository: PartyRoomRepositoryImpl = PartyRoomRepositoryImpl(persistenceRepository)

    @Test
    fun `should save party room and return domain entity`() {
        // given
        val domainPartyRoom = PartyRoom("Test Party")
        val persistencePartyRoom = PartyRoomEntity(
            id = 1L,
            name = "Test Party"
        )
        val savedPersistencePartyRoom = persistencePartyRoom.copy(id = 1L)

        whenever(persistenceRepository.save(any())).thenReturn(savedPersistencePartyRoom)

        // when
        val result = repository.save(domainPartyRoom)

        // then
        assertThat(result).isNotNull
        assertThat(result.description.name).isEqualTo("Test Party")
        assertThat(result.description.id).isEqualTo(1L)
    }

    @Test
    fun `should find party room by id and return domain entity`() {
        // given
        val persistencePartyRoom = PartyRoomEntity(
            id = 1L,
            name = "Test Party"
        )

        whenever(persistenceRepository.findById(1L)).thenReturn(Optional.of(persistencePartyRoom))

        // wheno
        val result = repository.findById(1L)

        // then
        assertThat(result).isPresent
        assertThat(result.get().description.name).isEqualTo("Test Party")
        assertThat(result.get().description.id).isEqualTo(1L)
    }

    @Test
    fun `should return empty optional when party room not found`() {
        // given
        whenever(persistenceRepository.findById(1L)).thenReturn(Optional.empty())

        // when
        val result = repository.findById(1L)

        // then
        assertThat(result).isEmpty
    }

    @Test
    fun `should find all party rooms and return domain entities`() {
        // given
        val persistencePartyRooms = listOf(
            PartyRoomEntity(id = 1L, name = "Party 1"),
            PartyRoomEntity(id = 2L, name = "Party 2")
        )

        val expectedPartyRooms = listOf(
            PartyRoom(1L, "Party 1"),
            PartyRoom(2L, "Party 2"),
        )

        whenever(persistenceRepository.findAll()).thenReturn(persistencePartyRooms)

        // when
        val result = repository.findAll()

        // then
        assert(result == expectedPartyRooms)
    }

    @Test
    fun `should delete all party rooms`() {
        // when
        repository.deleteAll()

        // then
        verify(persistenceRepository).deleteAll()
    }
} 