package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.domain.friends.entity.Friend
import com.partymakers.shareparty.domain.party.entity.PartyRoom
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class GetPartyFriendsImplTest {

    private val repository: PartyRoomRepository = mock()
    private val getPartyFriends: GetPartyFriendsImpl = GetPartyFriendsImpl(repository)

    @Test
    fun `should return set of friends for party room`() {
        // given
        val partyId = 1L
        val friends = setOf(
            Friend(
                name = "Friend 1",
                nickName = "nick1",
                eMail = "friend1@example.com"
            ),
            Friend(
                name = "Friend 2",
                nickName = "nick2",
                eMail = "friend2@example.com"
            )
        )
        val partyRoom = PartyRoom(
            id = partyId,
            name = "Test Party"
        ).copy(friends = friends)

        whenever(repository.findById(partyId)).thenReturn(Optional.of(partyRoom))

        // when
        val result = getPartyFriends.getPartyFriends(partyId)

        // then
        assert(result == friends)
        verify(repository).findById(partyId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `should throw NotFoundException when party room not found`() {
        // given
        val partyId = 1L
        whenever(repository.findById(partyId)).thenReturn(Optional.empty())

        // when/then
        assertThrows<NotFoundException> {
            getPartyFriends.getPartyFriends(partyId)
        }

        verify(repository).findById(partyId)
        verifyNoMoreInteractions(repository)
    }
} 