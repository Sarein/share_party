package com.partymakers.shareparty.application.party

import com.fasterxml.jackson.databind.ObjectMapper
import com.partymakers.shareparty.friends.presentation.dto.FriendDescription
import com.partymakers.shareparty.party.presentation.dto.InvitedFriendDescriptionDto
import com.partymakers.shareparty.party.presentation.dto.PartyRoomDescriptionDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class PartyControllerFriendsTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should get empty list of friends for new party`() {
        // given
        val partyName = "Test Party"
        val partyId = createParty(partyName)

        // when/then
        mockMvc.get("/api/v1/party/$partyId/friend") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content {
                json("""{"friends":[]}""")
            }
        }
    }

    @Test
    fun `should get list of friends for party with invited friends`() {
        // given
        val partyName = "Test Party"
        val partyId = createParty(partyName)
        createFriend("friend1", "Friend One", "friend1@test.com")
        createFriend("friend2", "Friend Two", "friend2@test.com")

        // Invite friends
        inviteFriend(partyId, "friend1")
        inviteFriend(partyId, "friend2")

        // when/then
        mockMvc.get("/api/v1/party/$partyId/friend") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content {
                json(
                    """
                   {
                      "friends":[
                         {
                            "name":"Friend Two",
                            "nickName":"friend2",
                            "email":"friend2@test.com"
                         },
                         {
                            "name":"Friend One",
                            "nickName":"friend1",
                            "email":"friend1@test.com"
                         }
                      ]
                   }
                """.trimIndent()
                )
            }
        }
    }

    @Test
    fun `should return 404 when party not found`() {
        // given
        val nonExistentPartyId = 999L

        // when/then
        mockMvc.get("/api/v1/party/$nonExistentPartyId/friend") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should invite friend to party`() {
        // given
        val partyName = "Test Party"
        val partyId = createParty(partyName)
        createFriend("testFriend", "Test Friend", "test@test.com")

        val request = InvitedFriendDescriptionDto("testFriend")

        // when/then
        mockMvc.post("/api/v1/party/$partyId/friend") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `should return 404 when friend not found`() {
        // given
        val partyName = "Test Party"
        val partyId = createParty(partyName)
        val request = InvitedFriendDescriptionDto("nonExistentFriend")

        // when/then
        mockMvc.post("/api/v1/party/$partyId/friend") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should return 208 when friend already invited`() {
        // given
        val partyName = "Test Party"
        val partyId = createParty(partyName)
        createFriend("testFriend", "Test Friend", "test@test.com")

        // First invitation
        mockMvc.post("/api/v1/party/$partyId/friend") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(InvitedFriendDescriptionDto("testFriend"))
        }

        // when/then - Second invitation
        mockMvc.post("/api/v1/party/$partyId/friend") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(InvitedFriendDescriptionDto("testFriend"))
        }.andExpect {
            status { isAlreadyReported() }
        }
    }

    private fun createParty(name: String): Long {
        val request = PartyRoomDescriptionDto(name)
        val response = mockMvc.post("/api/v1/party") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andReturn()

        val location = response.response.getHeader("Location")
        return location?.substringAfterLast("/")?.toLong()
            ?: throw IllegalStateException("Party ID not found in response")
    }

    private fun createFriend(nickName: String, name: String, email: String) {
        val request = FriendDescription(name, nickName, email)
        mockMvc.post("/api/v1/friend") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andReturn()
    }

    private fun inviteFriend(partyId: Long, nickName: String) {
        val request = InvitedFriendDescriptionDto(nickName)
        mockMvc.post("/api/v1/party/$partyId/friend") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
    }
} 