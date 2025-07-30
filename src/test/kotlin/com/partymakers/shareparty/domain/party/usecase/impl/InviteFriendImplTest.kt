package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.friends.domain.entity.Friend
import com.partymakers.shareparty.friends.domain.repository.FriendsRepository
import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.exception.AlreadyExistException
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.usecase.InviteFriendUseCaseImpl
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Optional

class InviteFriendImplTest {

    private val partyRoomRepository: PartyRoomRepository = mock()
    private val friendsRepository: FriendsRepository = mock()
    private val useCase = InviteFriendUseCaseImpl(partyRoomRepository, friendsRepository)

    @Test
    fun `should invite friend to party room`() {
        // given
        val partyId = 1L
        val nickName = "testFriend"
        val friend = Friend("Test", nickName, "test@test.com")
        val room = PartyRoom(partyId, "Test Party")
        
        whenever(friendsRepository.findOneById(nickName)).thenReturn(Optional.of(friend))
        whenever(partyRoomRepository.findById(partyId)).thenReturn(Optional.of(room))

        // when
        useCase.inviteFriend(nickName, partyId)

        // then
        verify(partyRoomRepository).save(any())
    }

    @Test
    fun `should throw NotFoundException when friend not found`() {
        // given
        val partyId = 1L
        val nickName = "testFriend"
        whenever(friendsRepository.findOneById(nickName)).thenReturn(Optional.empty())

        // when/then
        assertThatThrownBy { useCase.inviteFriend(nickName, partyId) }
            .isInstanceOf(NotFoundException::class.java)
    }

    @Test
    fun `should throw NotFoundException when party room not found`() {
        // given
        val partyId = 1L
        val nickName = "testFriend"
        val friend = Friend("Test", nickName, "test@test.com")
        
        whenever(friendsRepository.findOneById(nickName)).thenReturn(Optional.of(friend))
        whenever(partyRoomRepository.findById(partyId)).thenReturn(Optional.empty())

        // when/then
        assertThatThrownBy { useCase.inviteFriend(nickName, partyId) }
            .isInstanceOf(NotFoundException::class.java)
    }

    @Test
    fun `should throw AlreadyExistException when friend already invited`() {
        // given
        val partyId = 1L
        val nickName = "testFriend"
        val friend = Friend("Test", nickName, "test@test.com")
        val room = PartyRoom(partyId, "Test Party", setOf(friend), listOf())
        
        whenever(friendsRepository.findOneById(nickName)).thenReturn(Optional.of(friend))
        whenever(partyRoomRepository.findById(partyId)).thenReturn(Optional.of(room))

        // when/then
        assertThatThrownBy { useCase.inviteFriend(nickName, partyId) }
            .isInstanceOf(AlreadyExistException::class.java)
    }
} 