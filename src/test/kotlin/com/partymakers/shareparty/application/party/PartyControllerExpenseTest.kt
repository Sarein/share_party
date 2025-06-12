package com.partymakers.shareparty.application.party

import com.fasterxml.jackson.databind.ObjectMapper
import com.partymakers.shareparty.application.party.dto.Expense
import com.partymakers.shareparty.application.party.dto.PartyRoomDescription
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class PartyControllerExpenseTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should add expense to party`() {
        // given
        val partyName = "Test Party"
        val partyId = createParty(partyName)
        val expense = Expense("Pizza", 1000, 1.0)

        // when/then
        mockMvc.post("/api/v1/party/$partyId/expense") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(expense)
        }.andExpect {
            status { isCreated() }
        }
    }

    @Test
    fun `should get empty list of expenses for new party`() {
        // given
        val partyName = "Test Party"
        val partyId = createParty(partyName)

        // when/then
        mockMvc.get("/api/v1/party/$partyId/expense") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content {
                json("""{"expenses":[]}""")
            }
        }
    }

    @Test
    fun `should get list of expenses for party`() {
        // given
        val partyName = "Test Party"
        val partyId = createParty(partyName)
        val expense1 = Expense("Pizza", 1000, 1.0)
        val expense2 = Expense("Beer", 500, 2.0)

        // Add expenses
        addExpense(partyId, expense1)
        addExpense(partyId, expense2)

        // when/then
        mockMvc.get("/api/v1/party/$partyId/expense") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content {
                json(
                    """
                    {
                        "expenses": [
                            {
                                "name": "Pizza",
                                "cost": 1000,
                                "count": 1.0
                            },
                            {
                                "name": "Beer",
                                "cost": 500,
                                "count": 2.0
                            }
                        ]
                    }
                """.trimIndent()
                )
            }
        }
    }

    @Test
    fun `should remove expense from party`() {
        // given
        val partyName = "Test Party"
        val partyId = createParty(partyName)
        val expense = Expense("Pizza", 1000, 1.0)
        addExpense(partyId, expense)

        // when/then
        mockMvc.delete("/api/v1/party/$partyId/expense") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(expense)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `should return 404 when party not found for adding expense`() {
        // given
        val nonExistentPartyId = 999L
        val expense = Expense("Pizza", 1000, 1.0)

        // when/then
        mockMvc.post("/api/v1/party/$nonExistentPartyId/expense") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(expense)
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should return 404 when party not found for getting expenses`() {
        // given
        val nonExistentPartyId = 999L

        // when/then
        mockMvc.get("/api/v1/party/$nonExistentPartyId/expense") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should return 404 when party not found for removing expense`() {
        // given
        val nonExistentPartyId = 999L
        val expense = Expense("Pizza", 1000, 1.0)

        // when/then
        mockMvc.delete("/api/v1/party/$nonExistentPartyId/expense") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(expense)
        }.andExpect {
            status { isNotFound() }
        }
    }

    private fun createParty(name: String): Long {
        val request = PartyRoomDescription(name)
        val response = mockMvc.post("/api/v1/party") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andReturn()

        val location = response.response.getHeader("Location")
        return location?.substringAfterLast("/")?.toLong()
            ?: throw IllegalStateException("Party ID not found in response")
    }

    private fun addExpense(partyId: Long, expense: Expense) {
        mockMvc.post("/api/v1/party/$partyId/expense") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(expense)
        }
    }
} 