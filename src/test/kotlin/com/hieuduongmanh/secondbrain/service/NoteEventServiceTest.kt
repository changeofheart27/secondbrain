package com.hieuduongmanh.secondbrain.service

import com.hieuduongmanh.secondbrain.dto.NoteEventDTO
import com.hieuduongmanh.secondbrain.entity.Note
import com.hieuduongmanh.secondbrain.entity.NoteEvent
import com.hieuduongmanh.secondbrain.entity.NoteEventType
import com.hieuduongmanh.secondbrain.repository.NoteEventRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import java.time.Instant
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
class NoteEventServiceTest {
    @MockK
    private lateinit var noteEventRepository: NoteEventRepository

    private lateinit var noteEventService: NoteEventService

    @BeforeEach
    fun setUp() {
        noteEventService = NoteEventService(noteEventRepository)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getNoteEventsByNoteId should return note event when found`() {
        val note = Note(
            id = UUID.randomUUID(),
            title = "Test",
            content = "Content",
            createdAt = Instant.now(),
            updatedAt = null
        )
        val noteEvents = listOf(
            NoteEvent(
                UUID.randomUUID(),
                note,
                NoteEventType.CREATED,
                Instant.now(),
                null
            ),
            NoteEvent(
                UUID.randomUUID(),
                note,
                NoteEventType.PINNED,
                Instant.now(),
                null
            )
        )
        every { noteEventRepository.findAllByNoteId(note.id) } returns noteEvents

        val result = noteEventService.getNoteEventsByNoteId(noteId = note.id)

        assertEquals(2, result.size)
        assertTrue(result[0] is NoteEventDTO)
        verify { noteEventRepository.findAllByNoteId(note.id) }
    }
}