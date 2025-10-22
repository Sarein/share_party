package com.partymakers.shareparty.party.presentation.mapper

import com.partymakers.shareparty.party.presentation.dto.PartyRoomIdDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PartyRoomIdDtoMapperTest {

    private val mapper = PartyRoomIdDtoMapper()

    @Test
    fun `map Long id EXPECT PartyRoomIdDto`() {
        val id = 123L

        val result = mapper.toDto(id)

        assertThat(result).isEqualTo(
            PartyRoomIdDto(id = 123L)
        )
    }
}