package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.party.domain.entity.Expense
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
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class RemovePartyExpenseImplTest {

    private val repository: PartyRoomRepository = mock()
    private val removePartyExpense: RemovePartyExpenseUseCaseImpl = RemovePartyExpenseUseCaseImpl(repository)

    @Test
    fun `should remove expense from party room`() {
        // given
        val partyId = 1L
        val expenseToRemove = Expense(
            name = "Expense To Remove",
            cost = 100,
            count = 1.0
        )
        val otherExpense = Expense(
            name = "Other Expense",
            cost = 200,
            count = 2.0
        )
        val partyRoom = PartyRoom(
            id = partyId,
            name = "Test Party"
        ).copy(expenses = listOf(expenseToRemove, otherExpense))

        whenever(repository.findById(partyId)).thenReturn(Optional.of(partyRoom))

        // when
        removePartyExpense.removePartyExpense(partyId, expenseToRemove)

        // then
        verify(repository).findById(partyId)
        verify(repository).save(any())
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `should remove expense when it is the only one`() {
        // given
        val partyId = 1L
        val expenseToRemove = Expense(
            name = "Expense To Remove",
            cost = 100,
            count = 1.0
        )
        val partyRoom = PartyRoom(
            id = partyId,
            name = "Test Party"
        ).copy(expenses = listOf(expenseToRemove))

        whenever(repository.findById(partyId)).thenReturn(Optional.of(partyRoom))

        // when
        removePartyExpense.removePartyExpense(partyId, expenseToRemove)

        // then
        verify(repository).findById(partyId)
        verify(repository).save(any())
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `should throw NotFoundException when party room not found`() {
        // given
        val partyId = 1L
        val expense = Expense(
            name = "Test Expense",
            cost = 100,
            count = 1.0
        )
        whenever(repository.findById(partyId)).thenReturn(Optional.empty())

        // when/then
        assertThrows<NotFoundException> {
            removePartyExpense.removePartyExpense(partyId, expense)
        }

        verify(repository).findById(partyId)
        verifyNoMoreInteractions(repository)
    }
} 