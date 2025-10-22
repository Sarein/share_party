package com.partymakers.shareparty.application.party

import com.fasterxml.jackson.databind.ObjectMapper
import com.partymakers.shareparty.party.domain.entity.Expense
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.presentation.PartyControllerApiV1
import com.partymakers.shareparty.party.presentation.dto.ExpenseDto
import com.partymakers.shareparty.party.presentation.dto.PartyRoomDescriptionDto
import com.partymakers.shareparty.party.presentation.dto.PartyRoomDto
import com.partymakers.shareparty.party.presentation.dto.PartyRoomIdDto
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BasePartyTest {

    @Autowired
    internal lateinit var mockMvc: MockMvc

    @Autowired
    internal lateinit var objectMapper: ObjectMapper

    @Autowired
    internal lateinit var partyRoomRepository: PartyRoomRepository

    @BeforeEach
    internal fun setup() {
        partyRoomRepository.deleteAll()
    }

    internal fun addExpense(partyId: Long, expense: ExpenseDto): PartyRoomDto {
        val resultString = mockMvc.post("${PartyControllerApiV1.PARTY_BASE_URL}/$partyId/expense") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(expense)
        }.andExpect {
            status { isCreated() }
        }.andReturn()
            .response
            .getContentAsString()

        return objectMapper.readValue(resultString, PartyRoomDto::class.java)
    }

    internal fun getPartyRoom(partyId: Long): PartyRoomDto {
        val resultString = mockMvc.get("${PartyControllerApiV1.PARTY_BASE_URL}/$partyId") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }.andReturn()
            .response
            .getContentAsString()

        return objectMapper.readValue(resultString, PartyRoomDto::class.java)
    }

    internal fun deleteExpense(partyId: Long, expense: Long): PartyRoomDto {
        val resultString = mockMvc.delete("${PartyControllerApiV1.PARTY_BASE_URL}/$partyId/expense") {
            contentType = MediaType.APPLICATION_JSON
            param("expenseId",expense.toString())
        }.andExpect {
            status { isOk() }
        }.andReturn()
            .response
            .getContentAsString()

        return objectMapper.readValue(resultString, PartyRoomDto::class.java)
    }

    internal fun createParty(name: String): Long {
        val response = mockMvc.post("${PartyControllerApiV1.PARTY_BASE_URL}"){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(PartyRoomDescriptionDto(name))
        }
            .andExpect {
                status { isCreated() }
            }
            .andReturn()
            .response
            .getContentAsString()

        val responseValue = objectMapper.readValue(response, PartyRoomIdDto::class.java)
        return responseValue.id
    }
}