package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.usecase.RemovePartyExpenseUseCaseImpl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class RemovePartyExpenseImplTest {

    private val repository: PartyRoomRepository = mock()
    private val removePartyExpense: RemovePartyExpenseUseCaseImpl = RemovePartyExpenseUseCaseImpl(repository)

    @Test
    fun `should remove expense from party room`() {
        // given
        val partyId = 1L
        val expenseId = 1L
        val partyRoom = PartyRoom(
            id = partyId,
            name = "Test Party"
        )

        whenever(repository.deleteExpense(partyId, expenseId)).thenReturn(partyRoom)

        // when
        removePartyExpense.invoke(partyId, expenseId)

        // then
        verify(repository).deleteExpense(partyId, expenseId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `should remove expense when it is the only one`() {
        // given
        val partyId = 1L
        val expenseId = 1L
        val partyRoom = PartyRoom(
            id = partyId,
            name = "Test Party"
        )

        whenever(repository.deleteExpense(partyId, expenseId)).thenReturn(partyRoom)

        // when
        removePartyExpense.invoke(partyId, expenseId)

        // then
        verify(repository).deleteExpense(partyId, expenseId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `should throw NotFoundException when party room not found`() {
        // given
        val partyId = 1L
        val expenseId = 1L
        whenever(repository.deleteExpense(partyId, expenseId)).thenReturn(null)

        // when/then
        assertThrows<NotFoundException> {
            removePartyExpense.invoke(partyId, expenseId)
        }

        verify(repository).deleteExpense(partyId, expenseId)
        verifyNoMoreInteractions(repository)
    }
} 