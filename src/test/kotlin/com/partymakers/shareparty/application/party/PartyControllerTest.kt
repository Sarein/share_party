package com.partymakers.shareparty.application.party

import com.fasterxml.jackson.databind.ObjectMapper
import com.partymakers.shareparty.party.presentation.dto.PartyRoomDescription
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
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
            MockMvcRequestBuilders.get("/api/v1/parties")
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
            MockMvcRequestBuilders.get("/api/v1/parties")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.parties").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.parties.length()").value(3))
            .andExpect(MockMvcResultMatchers.jsonPath("$.parties[0].description.name").value("Party 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.parties[1].description.name").value("Party 2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.parties[2].description.name").value("Party 3"))
    }

    @Test
    fun `should return party room`() {
        // given
        val partyName = "Test Party"
        val request = PartyRoomDescription(partyName)

        val result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/party")
                    .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(MockMvcResultMatchers.status().isCreated)
            .andReturn()

        val location = result.response.getHeader("Location")
        val id = location?.substring(location.lastIndexOf('/') + 1)
        // then - verify list contains all created parties
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/party/$id")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.description.id").value(id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description.name").value(partyName))
    }
}