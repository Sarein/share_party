package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.friends.domain.entity.Friend
import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.usecase.KickFriendUseCaseImpl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class KickFriendImplTest {

    private val repository: PartyRoomRepository = mock()
    private val kickFriend: KickFriendUseCaseImpl = KickFriendUseCaseImpl(repository)

    @Test
    fun `should remove friend from party room`() {
        // given
        val partyId = 1L
        val friendToRemove = Friend(
            name = "Friend To Remove",
            nickName = "friend1",
            eMail = "friend1@example.com"
        )
        val otherFriend = Friend(
            name = "Other Friend",
            nickName = "friend2",
            eMail = "friend2@example.com"
        )
        val partyRoom = PartyRoom(
            id = partyId,
            name = "Test Party"
        ).copy(friends = setOf(friendToRemove, otherFriend))

        whenever(repository.deleteFriend(partyId, friendToRemove.nickName)).thenReturn(partyRoom)

        // when
        kickFriend.invoke(partyId, friendToRemove.nickName)

        // then
        verify(repository).deleteFriend(partyId, friendToRemove.nickName)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `should remove friend case-insensitively`() {
        // given
        val partyId = 1L
        val friendToRemove = Friend(
            name = "Friend To Remove",
            nickName = "Friend1",
            eMail = "friend1@example.com"
        )
        val partyRoom = PartyRoom(
            id = partyId,
            name = "Test Party"
        ).copy(friends = setOf(friendToRemove))

        whenever(repository.deleteFriend(partyId, "friend1")).thenReturn(partyRoom)

        // when
        kickFriend.invoke(partyId, "friend1")

        // then
        verify(repository).deleteFriend(partyId, "friend1")
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `should throw NotFoundException when party room not found`() {
        // given
        val partyId = 1L
        val nickName = "friend1"
        whenever(repository.deleteFriend(partyId, nickName)).thenReturn(null)

        // when/then
        assertThrows<NotFoundException> {
            kickFriend.invoke(partyId, nickName)
        }

        verify(repository).deleteFriend(partyId, nickName)
        verifyNoMoreInteractions(repository)
    }
} 