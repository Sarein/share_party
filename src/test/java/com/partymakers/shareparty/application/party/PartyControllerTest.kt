package com.partymakers.shareparty.application.party

import com.fasterxml.jackson.databind.ObjectMapper
import com.partymakers.shareparty.application.party.dto.PartyRoomDescription
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PartyControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should create party room and return 201 with location header`() {
        // given
        val partyName = "Integration Test Party"
        val request = PartyRoomDescription(partyName)

        // when/then
        mockMvc.perform(
            post("/api/v1/party")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(header().exists("Location"))
    }

    @Test
    fun `should return empty list when no party rooms exist`() {
        mockMvc.perform(
            get("/api/v1/party")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.parties").isArray)
            .andExpect(jsonPath("$.parties").isEmpty)
    }

    @Test
    fun `should return list of created party rooms`() {
        // given
        val partyNames = listOf("Party 1", "Party 2", "Party 3")

        // when - create party rooms
        partyNames.forEach { name ->
            mockMvc.perform(
                post("/api/v1/party")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(PartyRoomDescription(name)))
            )
                .andExpect(status().isCreated)
        }

        // then - verify list contains all created parties
        mockMvc.perform(
            get("/api/v1/party")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.parties").isArray)
            .andExpect(jsonPath("$.parties.length()").value(3))
            .andExpect(jsonPath("$.parties[0].name").value("Party 1"))
            .andExpect(jsonPath("$.parties[1].name").value("Party 2"))
            .andExpect(jsonPath("$.parties[2].name").value("Party 3"))
    }

    @Test
    fun `should return party room`() {
        // given
        val partyNames = listOf("Party 1", "Party 2", "Party 3")

        // when - create party rooms
        partyNames.forEach { name ->
            mockMvc.perform(
                post("/api/v1/party")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(PartyRoomDescription(name)))
            )
                .andExpect(status().isCreated)
        }

        // then - verify list contains all created parties
        mockMvc.perform(
            get("/api/v1/party/2")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value("2"))
            .andExpect(jsonPath("$.name").value("Party 2"))
    }
} 