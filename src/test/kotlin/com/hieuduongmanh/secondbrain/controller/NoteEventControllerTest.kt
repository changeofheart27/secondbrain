package com.hieuduongmanh.secondbrain.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.hieuduongmanh.secondbrain.dto.NoteEventDTO
import com.hieuduongmanh.secondbrain.entity.NoteEventType
import com.hieuduongmanh.secondbrain.service.NoteEventService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant
import java.util.*

@WebMvcTest(NoteEventController::class)
class NoteEventControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var noteEventService: NoteEventService

    @Test
    fun `GET all note events should return 200`() {
        val noteId = UUID.randomUUID()
        val noteEventDTO1 = NoteEventDTO(
            id = UUID.randomUUID(),
            noteId = noteId,
            eventType = NoteEventType.CREATED,
            createdAt = Instant.now(),
            updatedAt = null
        )
        val noteEventDTO2 = NoteEventDTO(
            id = UUID.randomUUID(),
            noteId = noteId,
            eventType = NoteEventType.PINNED,
            createdAt = Instant.now(),
            updatedAt = null
        )

        every { noteEventService.getNoteEventsByNoteId(noteId) } returns mutableListOf(noteEventDTO1, noteEventDTO2)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/note-events/$noteId"))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].eventType").value(NoteEventType.PINNED.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Get NoteEvents By Note ID Successful"))
    }
}