package com.hieuduongmanh.secondbrain.domain.service

import com.hieuduongmanh.secondbrain.api.exception.ResourceNotFoundException
import com.hieuduongmanh.secondbrain.domain.model.Note
import com.hieuduongmanh.secondbrain.domain.model.NoteEvent
import com.hieuduongmanh.secondbrain.domain.model.NoteEventType
import com.hieuduongmanh.secondbrain.domain.repository.NoteEventRepository
import com.hieuduongmanh.secondbrain.domain.repository.NoteRepository
import com.hieuduongmanh.secondbrain.domain.repository.TagRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class NoteService(
    private val noteRepository: NoteRepository,
    private val noteEventRepository: NoteEventRepository,
    private val tagRepository: TagRepository
) {
    fun getAllNotes(): List<Note> = noteRepository.findAll()

    fun getNoteById(noteId: UUID): Note {
        val note = noteRepository.findById(noteId)
            ?: throw ResourceNotFoundException("Note with id $noteId not found")

        return note
    }

    fun getNotesByTagId(tagId: UUID): List<Note> =
        noteRepository.findByTagsId(tagId)

    fun createNote(title: String?, content: String, tagName: String?): Note {
        val tag = tagRepository.findByName(tagName)
            ?: throw ResourceNotFoundException("Tag with name $tagName not found")

        val now = Instant.now()
        val note = Note(
            id = UUID.randomUUID(),
            title = title,
            content = content,
            createdAt = now,
            updatedAt = null,
            tags = setOf(tag)
        )

        val saved = noteRepository.save(note)

        noteEventRepository.save(
            NoteEvent(
                id = UUID.randomUUID(),
                noteId = saved.id,
                eventType = NoteEventType.CREATED,
                createdAt = now,
                updatedAt = null
            )
        )

        return saved
    }

    fun deleteNote(noteId: UUID) {
        noteRepository.findById(noteId)
            ?: throw ResourceNotFoundException("Note with id $noteId not found")

        noteRepository.delete(noteId)

        noteEventRepository.save(
            NoteEvent(
                id = UUID.randomUUID(),
                noteId = noteId,
                eventType = NoteEventType.DELETED,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
    }

    fun pinNote(noteId: UUID): Note {
        val now = Instant.now()
        val note = noteRepository.findById(noteId)
            ?: throw ResourceNotFoundException("Note with id $noteId not found")

        val updated = note.copy(isPinned = true, updatedAt = now)

        noteRepository.save(updated)

        noteEventRepository.save(
            NoteEvent(
                id = UUID.randomUUID(),
                noteId = noteId,
                eventType = NoteEventType.PINNED,
                createdAt = now,
                updatedAt = now
            )
        )

        return updated
    }
}
