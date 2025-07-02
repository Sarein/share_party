package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.party.domain.entity.Expense
import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.usecase.GetPartyExpensesUseCaseImpl
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
class GetPartyExpensesImplTest {

    private val repository: PartyRoomRepository = mock()
    private val getPartyExpenses: GetPartyExpensesUseCaseImpl = GetPartyExpensesUseCaseImpl(repository)

    @Test
    fun `should return list of expenses for party room`() {
        // given
        val partyId = 1L
        val expenses = listOf(
            Expense(name = "Test Expense 1", cost = 100, count = 1.0),
            Expense(name = "Test Expense 2", cost = 200, count = 2.0)
        )
        val partyRoom = PartyRoom(
            id = partyId,
            name = "Test Party"
        ).copy(expenses = expenses)

        whenever(repository.findById(partyId)).thenReturn(Optional.of(partyRoom))

        // when
        val result = getPartyExpenses.getPartyExpenses(partyId)

        // then
        assert(result == expenses)
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
            getPartyExpenses.getPartyExpenses(partyId)
        }

        verify(repository).findById(partyId)
        verifyNoMoreInteractions(repository)
    }
} 