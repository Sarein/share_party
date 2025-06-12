package com.partymakers.shareparty.application.party

import com.fasterxml.jackson.databind.ObjectMapper
import com.partymakers.shareparty.application.party.dto.PartyRoomDescription
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PartyControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var partyRoomRepository: PartyRoomRepository

    @BeforeEach
    fun setup() {
        partyRoomRepository.deleteAll()
    }

    @Test
    fun `should create party room and return 201 with location header`() {
        // given
        val partyName = "Integration Test Party"
        val request = PartyRoomDescription(partyName)

        // when/then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/party")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.header().exists("Location"))
    }

    @Test
    fun `should return empty list when no party rooms exist`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/party")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.parties").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.parties").isEmpty)
    }

    @Test
    fun `should return list of created party rooms`() {
        // given
        val partyNames = listOf("Party 1", "Party 2", "Party 3")

        // when - create party rooms
        partyNames.forEach { name ->
            mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/party")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(PartyRoomDescription(name)))
            )
                .andExpect(MockMvcResultMatchers.status().isCreated)
        }

        // then - verify list contains all created parties
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/party")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.parties").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.parties.length()").value(3))
            .andExpect(MockMvcResultMatchers.jsonPath("$.parties[0].name").value("Party 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.parties[1].name").value("Party 2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.parties[2].name").value("Party 3"))
    }

    @Test
    fun `should return party room`() {
        // given
        val partyNames = listOf("Party 1", "Party 2", "Party 3")

        // when - create party rooms
        partyNames.forEach { name ->
            mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/party")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(PartyRoomDescription(name)))
            )
                .andExpect(MockMvcResultMatchers.status().isCreated)
        }

        // then - verify list contains all created parties
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/party/2")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Party 2"))
    }
}