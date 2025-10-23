package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.friends.domain.entity.Friend
import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.entity.PartyRoomDescription
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.usecase.InviteFriendUseCaseImpl
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class InviteFriendImplTest {

    private val repository: PartyRoomRepository = mock()
    private val inviteFriendUseCase = InviteFriendUseCaseImpl(repository)

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
    fun `invite friend to party EXPECT call repository`() {
        whenever(repository.existsById(partyId)).thenReturn(true)
        whenever(repository.findById(partyId)).thenReturn(partyRoom)

        // when
        inviteFriendUseCase(nickName1,partyId)

        // then
        verify(repository).addFriend(partyId, nickName1)
    }

    @Test
    fun `invite friend to party room EXPECT return it`() {
        whenever(repository.existsById(partyId)).thenReturn(true)
        whenever(repository.findById(partyId)).thenReturn(partyRoom)

        // when
        val result = inviteFriendUseCase(nickName1,partyId)


        assertEquals(partyRoom,  result)
    }

    @Test
    fun `party room doesn't exist EXPECT throw NotFoundException`() {
        whenever(repository.existsById(partyId)).thenReturn(true)
        whenever(repository.findById(partyId)).thenReturn(null)

        // when/then
        assertThrows<NotFoundException> {
            inviteFriendUseCase(nickName1,partyId)
        }
    }
} 