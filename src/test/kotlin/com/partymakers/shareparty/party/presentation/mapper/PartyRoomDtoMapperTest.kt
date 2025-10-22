package com.partymakers.shareparty.party.presentation.mapper

import com.partymakers.shareparty.friends.domain.entity.Friend
import com.partymakers.shareparty.party.domain.entity.Expense
import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.entity.PartyRoomDescription
import com.partymakers.shareparty.party.presentation.dto.PartyRoomDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PartyRoomDtoMapperTest {

    private val mapper = PartyRoomDtoMapper()

    @Test
    fun `map PartyRoom to PartyRoomDto with all fields`() {
        // given
        val friends = setOf(
            Friend("John", "johnny", "john@example.com"),
            Friend("Jane", "jane", "jane@example.com")
        )
        val expenses = listOf(
            Expense(1L, "Pizza", 2000, 1.0),
            Expense(2L, "Drinks", 500, 4.0)
        )
        val partyRoom = PartyRoom(
            description = PartyRoomDescription(123L, "Test Party"),
            friends = friends,
            expenses = expenses
        )

        // when
        val result = mapper.toDto(partyRoom)

        // then
        assertThat(result).isEqualTo(
            PartyRoomDto(
                id = 123L,
                name = "Test Party",
                friends = friends,
                expenses = expenses
            )
        )
    }
}