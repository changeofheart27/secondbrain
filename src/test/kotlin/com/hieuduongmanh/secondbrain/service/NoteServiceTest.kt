package com.hieuduongmanh.secondbrain.service

import com.hieuduongmanh.secondbrain.dto.NoteDTO
import com.hieuduongmanh.secondbrain.dto.request.CreateNoteRequest
import com.hieuduongmanh.secondbrain.entity.Note
import com.hieuduongmanh.secondbrain.entity.Tag
import com.hieuduongmanh.secondbrain.exception.ResourceNotFoundException
import com.hieuduongmanh.secondbrain.repository.NoteEventRepository
import com.hieuduongmanh.secondbrain.repository.NoteRepository
import com.hieuduongmanh.secondbrain.repository.TagRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import java.time.Instant
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
class NoteServiceTest {
    @MockK
    private lateinit var noteRepository: NoteRepository

    @MockK
    private lateinit var noteEventRepository: NoteEventRepository

    @MockK
    private lateinit var tagRepository: TagRepository

    private lateinit var noteService: NoteService

    @BeforeEach
    fun setUp() {
        // no need to initialize since we are using @Mockk and @ExtendWith(MockKExtension::class) extension
        // noteRepository = mockk()
        // noteEventRepository = mockk()
        // tagRepository = mockk()
        noteService = NoteService(noteRepository, noteEventRepository, tagRepository)
    }

    @AfterEach
    fun tearDown() {
        // reset mock behavior after each test
        clearAllMocks()
    }

    @Test
    fun `getAllNotes should return all notes`() {
        val notes = listOf(
            Note(
                id = UUID.randomUUID(),
                title = "Test Note 1",
                content = "Content Note 1",
                createdAt = Instant.now(),
                updatedAt = null
            ),
            Note(
                id = UUID.randomUUID(),
                title = "Test Note 2",
                content = "Content Note 2",
                createdAt = Instant.now(),
                updatedAt = null
            )
        )
        every { noteRepository.findAll() } returns notes

        val result = noteService.getAllNotes()

        assertEquals(2, result.size)
        assertTrue(result[0] is NoteDTO)
        verify { noteRepository.findAll() }
    }

    @Test
    fun `getNoteById should return note when found`() {
        val noteId = UUID.randomUUID()
        val note = Note(
            id = noteId,
            title = "Test",
            content = "Content",
            createdAt = Instant.now(),
            updatedAt = null
        )

        every { noteRepository.findById(noteId) } returns Optional.of(note)

        val result = noteService.getNoteById(noteId)

        assertEquals(noteId, result.id)
        verify { noteRepository.findById(noteId) }
    }

    @Test
    fun `getNoteById should throw exception when not found`() {
        val noteId = UUID.randomUUID()

        every { noteRepository.findById(noteId) } returns Optional.empty()

        assertFailsWith<ResourceNotFoundException> {
            noteService.getNoteById(noteId)
        }
    }

    @Test
    fun `createNote should save note and event`() {
        val tag = Tag(UUID.randomUUID(), "test")

        every { tagRepository.findByName("test") } returns tag
        every { noteRepository.save(any()) } answers { firstArg() }
        every { noteEventRepository.save(any()) } answers { firstArg() }

        val result = noteService.createNote(
            title = "Title",
            content = "Content",
            tagName = "test"
        )

        assertEquals("Title", result.title)

        verify { noteRepository.save(any()) }
        verify { noteEventRepository.save(any()) }
    }

    @Test
    fun `updateNote should update fields and save event`() {
        val noteId = UUID.randomUUID()

        val existingNote = Note(
            id = noteId,
            title = "Old",
            content = "Old Content",
            createdAt = Instant.now(),
            updatedAt = null
        )

        val tag = Tag(UUID.randomUUID(), "updatedTag")

        every { noteRepository.findById(noteId) } returns Optional.of(existingNote)
        every { tagRepository.findByName("updatedTag") } returns tag
        every { noteRepository.save(any()) } answers { firstArg() }
        every { noteEventRepository.save(any()) } answers { firstArg() }

        val request = CreateNoteRequest(
            title = "New",
            content = "New Content",
            tagName = "updatedTag"
        )

        val result = noteService.updateNote(noteId, request)

        assertEquals("New", result.title)
        assertEquals("New Content", result.content)
        assertTrue(result.tagDTOs!!.isNotEmpty())

        verify { noteRepository.save(existingNote) }
        verify { noteEventRepository.save(any()) }
    }

    @Test
    fun `deleteNote should call deleteById`() {
        val noteId = UUID.randomUUID()
        every { noteRepository.findById(noteId) } returns Optional.of(mockk())
        every { noteRepository.deleteById(noteId) } just Runs

        noteService.deleteNote(noteId)

        verify { noteRepository.deleteById(noteId) }
    }

    @Test
    fun `pinNote should set isPinned true and save event`() {
        val noteId = UUID.randomUUID()

        val note = Note(
            id = noteId,
            title = "Title",
            content = "Content",
            createdAt = Instant.now(),
            updatedAt = null
        )

        every { noteRepository.findById(noteId) } returns Optional.of(note)
        every { noteRepository.save(any()) } answers { firstArg() }
        every { noteEventRepository.save(any()) } answers { firstArg() }

        val result = noteService.pinNote(noteId)

        assertTrue(result.isPinned)

        verify { noteRepository.save(any()) }
        verify { noteEventRepository.save(any()) }
    }
}
