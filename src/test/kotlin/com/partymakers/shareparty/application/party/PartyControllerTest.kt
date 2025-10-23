package com.partymakers.shareparty.application.party

import com.partymakers.shareparty.party.presentation.PartyApiV1
import com.partymakers.shareparty.party.presentation.dto.ExpenseDto
import com.partymakers.shareparty.party.presentation.dto.InvitedFriendDescriptionDto
import com.partymakers.shareparty.party.presentation.dto.PartyRoomDescriptionDto
import com.partymakers.shareparty.party.presentation.dto.PartyRoomDto
import com.partymakers.shareparty.party.presentation.dto.PartyRoomIdDto
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@Sql(
    scripts = ["classpath:data/fill_friends.sql"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
    config = SqlConfig(encoding = "utf-8"),
)
@Sql(
    scripts = ["classpath:data/truncate_data.sql"],
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    config = SqlConfig(encoding = "utf-8"),
)
class PartyControllerTest : BasePartyTest(){

    private val firstFriendNickName = "ivan_ivanov"
    private val secondFriendNickName = "petr_petrov"

    @Test
    fun `should create party room and return 201 with location header`() {
        // given
        val partyName = "Integration Test Party"
        val request = PartyRoomDescriptionDto(partyName)

        // when/then
        mockMvc.perform(
            MockMvcRequestBuilders.post("${PartyApiV1.PARTY_BASE_URL}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun `should return empty list when no party rooms exist`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("${PartyApiV1.PARTY_BASE_URL}/parties")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty)
    }

    @Test
    fun `should return list of created party rooms`() {
        // given
        val partyNames = listOf("Party 1", "Party 2", "Party 3")

        // when - create party rooms
        partyNames.forEach { name ->
            mockMvc.perform(
                MockMvcRequestBuilders.post("${PartyApiV1.PARTY_BASE_URL}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(PartyRoomDescriptionDto(name)))
            )
                .andExpect(MockMvcResultMatchers.status().isCreated)
        }

        // then - verify list contains all created parties
        mockMvc.perform(
            MockMvcRequestBuilders.get("${PartyApiV1.PARTY_BASE_URL}/parties")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(3))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Party 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name").value("Party 2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].name").value("Party 3"))
    }

    @Test
    fun `should return party room`() {
        // given
        val partyName = "Test Party"
        val request = PartyRoomDescriptionDto(partyName)

        val result = mockMvc.perform(
                MockMvcRequestBuilders.post("${PartyApiV1.PARTY_BASE_URL}")
                    .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(MockMvcResultMatchers.status().isCreated)
            .andReturn()
            .response
            .getContentAsString()

        val responseValue = objectMapper.readValue(result, PartyRoomIdDto::class.java)
        val id = responseValue.id
        // then - verify list contains all created parties
        mockMvc.perform(
            MockMvcRequestBuilders.get("${PartyApiV1.PARTY_BASE_URL}/$id")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(partyName))
    }

    @Test
    fun `should get empty list of expenses for new party`() {
        val partyName = "Test Party"
        val partyId = createParty(partyName)

        val resultString = mockMvc.get("${PartyApiV1.PARTY_BASE_URL}/$partyId") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }
            .andReturn()
            .response
            .getContentAsString()

        val responseValue = objectMapper.readValue(resultString, PartyRoomDto::class.java)

        assertEquals(0, responseValue.expenses.size)
    }

    @Test
    fun `should add expense to party`() {
        val partyName = "Test Party"
        val partyId = createParty(partyName)
        val expense = ExpenseDto(id = "1","Pizza", 1000, 1.0)

        mockMvc.post("${PartyApiV1.PARTY_BASE_URL}/$partyId/expense") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(expense)
        }.andExpect {
            status { isCreated() }
        }
    }

    @Test
    fun `should get list of expenses for party`() {
        val partyName = "Test Party"
        val partyId = createParty(partyName)
        val expense1Name = "Pizza"
        val expense2Name = "Beer"
        val expense1 = ExpenseDto(id = null, name = expense1Name, 1000, 1.0)
        val expense2 = ExpenseDto(id = null,expense2Name, 500, 2.0)
        val expectedExpenses = listOf(expense1, expense2)
        addExpense(partyId, expense1)
        addExpense(partyId, expense2)

        val result = getPartyRoom(partyId)

        assertEquals(expectedExpenses.size, result.expenses.size)
        assertEquals(
            2,
            result.expenses.filter { it.name == expense1Name || it.name == expense2Name }.size
        )
    }

    @Test
    fun `should remove expense of expenses for party`() {
        val partyName = "Test Party"
        val partyId = createParty(partyName)
        val expense1Name = "Pizza"
        val expense2Name = "Beer"
        val expense1 = ExpenseDto(id = null, name = expense1Name, 1000, 1.0)
        val expense2 = ExpenseDto(id = null,expense2Name, 500, 2.0)
        addExpense(partyId, expense1)
        addExpense(partyId, expense2)

        val partyRoomWithTwoExpenses = getPartyRoom(partyId)
        val firstExpense = partyRoomWithTwoExpenses.expenses.find { it.name == expense1.name }

        val partyRoomWithOneExpenses = deleteExpense(partyId, firstExpense!!.id!!)
        assertEquals(
            1,
            partyRoomWithOneExpenses.expenses.filter { it.name == expense2Name }.size
        )
    }

    @Test
    fun `should return 404 when party not found for removing expense`() {
        // given
        val nonExistentPartyId = 999L

        // when/then
        mockMvc.delete("${PartyApiV1.PARTY_BASE_URL}/$nonExistentPartyId/expense") {
            contentType = MediaType.APPLICATION_JSON
            param("expenseId","10")
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should return 404 when party not found for adding expense`() {
        // given
        val nonExistentPartyId = 999L
        val expense = ExpenseDto(id = null,"Pizza", 1000, 1.0)

        // when/then
        mockMvc.post("${PartyApiV1.PARTY_BASE_URL}/$nonExistentPartyId/expense") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(expense)
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should get empty list of friends for new party`() {
        // given
        val partyName = "Test Party"
        val partyId = createParty(partyName)

        val partyRoom = getPartyRoom(partyId)

        assertTrue(partyRoom.friends.isEmpty())
    }

    @Test
    fun `should get list of friends for party with invited friends`() {
        val partyName = "Test Party"
        val partyId = createParty(partyName)

        inviteFriend(partyId, firstFriendNickName)
        inviteFriend(partyId, secondFriendNickName)

        val partyRoom = getPartyRoom(partyId)

        assertTrue(
            partyRoom.friends.filter {
                it.nickName == firstFriendNickName ||
                it.nickName == secondFriendNickName
            }.size == 2
        )
    }

    @Test
    fun `should return 404 when invite friend and party not found`() {
        // given
        val nonExistentPartyId = 999L

        mockMvc.post("${PartyApiV1.PARTY_BASE_URL}/$nonExistentPartyId/friend") {
            contentType = MediaType.APPLICATION_JSON
            param("nickName", firstFriendNickName)
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should return 200 when friend already invited`() {
        // given
        val partyName = "Test Party"
        val partyId = createParty(partyName)

        inviteFriend(partyId, firstFriendNickName)

        mockMvc.post("${PartyApiV1.PARTY_BASE_URL}/$partyId/friend") {
            contentType = MediaType.APPLICATION_JSON
            param("nickName",firstFriendNickName)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `should get list of friends for party with invited friends after remove first one`() {
        val partyName = "Test Party"
        val partyId = createParty(partyName)

        inviteFriend(partyId, firstFriendNickName)
        inviteFriend(partyId, secondFriendNickName)

        removeFriend(partyId, secondFriendNickName)

        val partyRoom = getPartyRoom(partyId)

        assertTrue(
            partyRoom.friends.filter {
                it.nickName == firstFriendNickName
            }.size == 1
        )
    }

    @Test
    fun `should return 404 when invited friend in a party not found`() {
        // given
        val nonExistentPartyId = 999L

        mockMvc.delete("${PartyApiV1.PARTY_BASE_URL}/$nonExistentPartyId/friend") {
            contentType = MediaType.APPLICATION_JSON
            param("nickName", firstFriendNickName)
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should return 200 when friend doesn't exist in party room`() {
        // given
        val partyName = "Test Party"
        val partyId = createParty(partyName)

        removeFriend(partyId, firstFriendNickName)

        mockMvc.post("${PartyApiV1.PARTY_BASE_URL}/$partyId/friend") {
            contentType = MediaType.APPLICATION_JSON
            param("nickName",firstFriendNickName)
        }.andExpect {
            status { isOk() }
        }
    }
}