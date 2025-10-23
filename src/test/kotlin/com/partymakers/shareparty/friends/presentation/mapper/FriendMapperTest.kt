package com.partymakers.shareparty.friends.presentation.mapper

import com.partymakers.shareparty.friends.domain.entity.Friend
import com.partymakers.shareparty.friends.presentation.dto.FriendDescriptionDto
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test

class FriendMapperTest {

    private val mapper = FriendDtoMapper()
    private val name: String = "name"
    private val nickName: String = "nickName"
    private val eMail: String = "eMail"

    private val friend = Friend(name, nickName, eMail)
    private val friendDto = FriendDescriptionDto(name, nickName, eMail)

    @Test
    fun `map dto to model EXPECT model`() {
        val model = mapper.toModel(friendDto)

        assertEquals(friend, model)
    }
}
