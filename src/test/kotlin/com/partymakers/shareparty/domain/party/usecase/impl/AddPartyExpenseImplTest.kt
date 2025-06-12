package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.domain.party.entity.Expense
import com.partymakers.shareparty.domain.party.entity.PartyRoom
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.jupiter.MockitoExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.whenever
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class AddPartyExpenseImplTest {

    @Mock
    private lateinit var repository: PartyRoomRepository
    private lateinit var addPartyExpense: AddPartyExpenseImpl

    @BeforeEach
    fun setup() {
        addPartyExpense = AddPartyExpenseImpl(repository)
    }

    @Test
    fun `should add expense to party room`() {
        // given
        val partyId = 1L
        val expense = Expense(
            name = "Test Expense",
            cost = 100,
            count = 1.0
        )
        val partyRoom = PartyRoom(
            id = partyId,
            name = "Test Party"
        )
        val updatedPartyRoom = partyRoom.copy(expenses = listOf(expense))

        whenever(repository.findById(partyId)).thenReturn(Optional.of(partyRoom))
        whenever(repository.save(any())).thenReturn(updatedPartyRoom)

        // when
        addPartyExpense.addPartyExpense(expense, partyId)

        // then
        verify(repository).findById(partyId)
        verify(repository).save(argThat { expenses.contains(expense) })
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
            addPartyExpense.addPartyExpense(expense, partyId)
        }

        verify(repository).findById(partyId)
        verifyNoMoreInteractions(repository)
    }
} 