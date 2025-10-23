package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.friends.domain.entity.Friend
import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.entity.PartyRoomDescription
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.usecase.KickFriendUseCaseImpl
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class KickFriendImplTest {

    private val repository: PartyRoomRepository = mock()
    private val kickFriendUseCase = KickFriendUseCaseImpl(repository)

    private val name1 = "Иван Иванов"
    private val nickName1 = "ivan_ivanov"
    private val eMail1 = "ivan@example.com"
    private val name2 = "Петр Петров"
    private val nickName2 = "petr_petrov"
    private val eMail2 = "petr@example.com"
    private val partyId = 1L
    private val friend1 = Friend(
        name = name1,
        nickName = nickName1,
        mail = eMail1,
    )
    private val friend2 = Friend(
        name = name2,
        nickName = nickName2,
        mail = eMail2,
    )

    private val partyRoom = PartyRoom(
        description = PartyRoomDescription(partyId, "name"),
        friends = setOf(friend1, friend2)
    )


    @Test
    fun `kick friend to party EXPECT call repository`() {
        whenever(repository.existsById(partyId)).thenReturn(true)
        whenever(repository.findById(partyId)).thenReturn(partyRoom)

        // when
        kickFriendUseCase(partyId, nickName1)

        // then
        verify(repository).deleteFriend(partyId, nickName1)
    }

    @Test
    fun `kick friend to party room EXPECT return it`() {
        whenever(repository.existsById(partyId)).thenReturn(true)
        whenever(repository.findById(partyId)).thenReturn(partyRoom)

        // when
        val result = kickFriendUseCase(partyId, nickName1)


        assertEquals(partyRoom,  result)
    }

    @Test
    fun `party room doesn't exist EXPECT throw NotFoundException`() {
        whenever(repository.existsById(partyId)).thenReturn(true)
        whenever(repository.findById(partyId)).thenReturn(null)

        // when/then
        assertThrows<NotFoundException> {
            kickFriendUseCase(partyId, nickName1)
        }
    }
} 