package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.party.domain.entity.Expense
import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.entity.PartyRoomDescription
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.usecase.AddPartyExpenseUseCaseImpl
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class AddPartyExpenseImplTest {

    @Mock
    private lateinit var repository: PartyRoomRepository
    private lateinit var addPartyExpense: AddPartyExpenseUseCaseImpl

    @BeforeEach
    fun setup() {
        addPartyExpense = AddPartyExpenseUseCaseImpl(repository)
    }

    @Test
    fun `should add expense to party room`() {
        // given
        val partyId = 1L
        val expense = Expense(
            id = null,
            name = "Test Expense",
            cost = 100,
            count = 1.0
        )
        val partyRoom = PartyRoom(
            id = partyId,
            name = "Test Party"
        )

        whenever(repository.existsById(partyId)).thenReturn(true)
        whenever(repository.findById(partyId)).thenReturn(partyRoom)

        // when
        addPartyExpense.invoke(partyId, expense)

        // then
        verify(repository).addExpense(partyId, expense)
    }

    @Test
    fun `should add expense to party room and return it`() {
        // given
        val partyId = 1L
        val newExpenseId = 13L
        val expenseWithId = Expense(
            id = newExpenseId,
            name = "Test Expense",
            cost = 100,
            count = 1.0
        )
        val expense = Expense(
            id = null,
            name = "Test Expense",
            cost = 100,
            count = 1.0
        )
        val partyRoom = PartyRoom(
            description = PartyRoomDescription(partyId, "name"),
            expenses = listOf(expenseWithId)
        )

        whenever(repository.existsById(partyId)).thenReturn(true)
        whenever(repository.findById(partyId)).thenReturn(partyRoom)

        // when
        val result = addPartyExpense.invoke(partyId, expense)


        assertEquals(partyRoom,  result)
    }

    @Test
    fun `should throw NotFoundException when party room doesn't exist`() {
        // given
        val partyId = 1L
        val expense = Expense(
            id = null,
            name = "Test Expense",
            cost = 100,
            count = 1.0
        )

        whenever(repository.existsById(partyId)).thenReturn(true)
        whenever(repository.findById(partyId)).thenReturn(null)

        // when/then
        assertThrows<NotFoundException> {
            addPartyExpense.invoke(partyId, expense)
        }
    }

    @Test
    fun `should throw NotFoundException when party room not found`() {
        // given
        val partyId = 1L
        val expense = Expense(
            id = null,
            name = "Test Expense",
            cost = 100,
            count = 1.0
        )

        whenever(repository.existsById(partyId)).thenReturn(false)

        // when/then
        assertThrows<NotFoundException> {
            addPartyExpense.invoke(partyId, expense)
        }
    }
} 