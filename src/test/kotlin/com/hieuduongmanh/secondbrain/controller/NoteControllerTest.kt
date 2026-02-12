package com.hieuduongmanh.secondbrain.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.hieuduongmanh.secondbrain.dto.NoteDTO
import com.hieuduongmanh.secondbrain.dto.TagDTO
import com.hieuduongmanh.secondbrain.dto.request.CreateNoteRequest
import com.hieuduongmanh.secondbrain.service.NoteService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant

import java.util.UUID
import kotlin.test.Test

@WebMvcTest(NoteController::class)
class NoteControllerWebTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var noteService: NoteService

    @Test
    fun `GET all notes should return 200`() {
        val noteId = UUID.randomUUID()
        val now = Instant.now()
        val tag = TagDTO(
            id = noteId,
            name = "Tag",
            createdAt = now
        )
        val noteDTO1 = NoteDTO(
            id = UUID.randomUUID(),
            title = "Test",
            content = "Content",
            tagDTOs = mutableSetOf(tag),
            createdAt = Instant.now(),
            updatedAt = null
        )
        val noteDTO2 = noteDTO1.copy(id = UUID.randomUUID(), title = "Test 2")

        every { noteService.getAllNotes() } returns mutableListOf(noteDTO1, noteDTO2)

        mockMvc.perform(get("/api/v1/notes"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.data[1].title").value("Test 2"))
            .andExpect(jsonPath("$.message").value("Get All Notes Successful"))
    }

    @Test
    fun `GET note by id should return 200`() {
        val noteId = UUID.randomUUID()
        val now = Instant.now()
        val tag = TagDTO(
            id = noteId,
            name = "Tag",
            createdAt = now
        )
        val noteDTO = NoteDTO(
            id = UUID.randomUUID(),
            title = "Test",
            content = "Content",
            tagDTOs = mutableSetOf(tag),
            createdAt = Instant.now(),
            updatedAt = null
        )

        every { noteService.getNoteById(noteId) } returns noteDTO

        mockMvc.perform(get("/api/v1/notes/$noteId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.data.title").value("Test"))
            .andExpect(jsonPath("$.message").value("Get Note By ID Successful"))
    }

    @Test
    fun `POST new note should return 201`() {
        val noteId = UUID.randomUUID()
        val now = Instant.now()
        val tag = TagDTO(
            id = noteId,
            name = "Tag",
            createdAt = now
        )
        val noteDTO = NoteDTO(
            id = UUID.randomUUID(),
            title = "Test",
            content = "Content",
            tagDTOs = mutableSetOf(tag),
            createdAt = Instant.now(),
            updatedAt = null
        )
        val createNoteRequest = CreateNoteRequest(noteDTO.title, noteDTO.content, tag.name)

        every { noteService.createNote(
            createNoteRequest.title,
            createNoteRequest.content,
            createNoteRequest.tagName
        ) } returns noteDTO

        mockMvc.perform(post("/api/v1/notes")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(createNoteRequest)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(201))
            .andExpect(jsonPath("$.data.title").value("Test"))
            .andExpect(jsonPath("$.message").value("Create Note Successful"))
    }

    @Test
    fun `PUT note should return 200`() {
        val noteId = UUID.randomUUID()
        val now = Instant.now()
        val tag = TagDTO(
            id = noteId,
            name = "Tag",
            createdAt = now
        )
        val updatedNoteDTO = NoteDTO(
            id = UUID.randomUUID(),
            title = "Updated Test",
            content = "Updated Content",
            tagDTOs = mutableSetOf(tag),
            createdAt = Instant.now(),
            updatedAt = null
        )
        val createNoteRequest = CreateNoteRequest(updatedNoteDTO.title, updatedNoteDTO.content, tag.name)


        every { noteService.updateNote(eq(noteId), any()) } returns updatedNoteDTO

        mockMvc.perform(put("/api/v1/notes/$noteId")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(createNoteRequest)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.data.title").value("Updated Test"))
            .andExpect(jsonPath("$.message").value("Update Note Successful"))
    }

    @Test
    fun `DELETE note should return 204`() {
        val testNoteId = UUID.randomUUID()
        every { noteService.deleteNote(testNoteId) } just Runs

        mockMvc.perform(delete("/api/v1/notes/$testNoteId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(204))
            .andExpect(jsonPath("$.message").value("Delete Note Successful"))
    }

    @Test
    fun `POST pin note should return 200`() {
        val noteId = UUID.randomUUID()
        val now = Instant.now()
        val tag = TagDTO(
            id = noteId,
            name = "Tag",
            createdAt = now
        )
        val noteDTO = NoteDTO(
            id = noteId,
            title = "Test",
            content = "Content",
            tagDTOs = mutableSetOf(tag),
            isPinned = true,
            createdAt = Instant.now(),
            updatedAt = null
        )

        every { noteService.pinNote(noteId) } returns noteDTO

        mockMvc.perform(post("/api/v1/notes/$noteId/pin"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.data.title").value("Test"))
            .andExpect(jsonPath("$.data.isPinned").value(true))
            .andExpect(jsonPath("$.message").value("Pin Note Successful"))
    }
}
