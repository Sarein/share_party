package com.partymakers.shareparty.domain.party.usecase.impl

import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.usecase.RemovePartyExpenseUseCaseImpl
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
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
        val partyRoom: PartyRoom = mock()

        whenever(repository.findById(partyId)).thenReturn(partyRoom)

        // when
        val result = removePartyExpense.invoke(partyId, expenseId)

        // then
        assertEquals(result, partyRoom)
    }


    @Test
    fun `should throw NotFoundException when party room not found`() {
        // given
        val partyId = 1L
        val expenseId = 1L

        whenever(repository.findById(partyId)).thenReturn(null)

        // when/then
        assertThrows<NotFoundException> {
            removePartyExpense.invoke(partyId, expenseId)
        }
    }
} 