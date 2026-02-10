package com.hieuduongmanh.secondbrain.service

import com.hieuduongmanh.secondbrain.exception.ResourceNotFoundException
import com.hieuduongmanh.secondbrain.dto.NoteDTO
import com.hieuduongmanh.secondbrain.dto.mapper.toDTO
import com.hieuduongmanh.secondbrain.dto.mapper.toEntity
import com.hieuduongmanh.secondbrain.dto.request.CreateNoteRequest
import com.hieuduongmanh.secondbrain.entity.Note
import com.hieuduongmanh.secondbrain.entity.NoteEvent
import com.hieuduongmanh.secondbrain.entity.NoteEventType
import com.hieuduongmanh.secondbrain.repository.NoteEventRepository
import com.hieuduongmanh.secondbrain.repository.NoteRepository
import com.hieuduongmanh.secondbrain.repository.TagRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class NoteService(
    private val noteRepository: NoteRepository,
    private val noteEventRepository: NoteEventRepository,
    private val tagRepository: TagRepository
) {
    fun getAllNotes(): List<NoteDTO> = noteRepository.findAll().map { it.toDTO() }

    fun getNoteById(noteId: UUID): NoteDTO {
        val note = noteRepository.findById(noteId).orElseThrow {
            ResourceNotFoundException("Note with id $noteId not found")
        }
        return note.toDTO()
    }

    fun getNotesByTagId(tagId: UUID): List<NoteDTO> =
        noteRepository.findByTagsId(tagId).map { it.toDTO() }

    fun createNote(title: String?, content: String, tagName: String?): NoteDTO {
        val tag = tagRepository.findByName(tagName)
            ?: throw ResourceNotFoundException("Tag with name $tagName not found")

        val now = Instant.now()
        val noteDTO = Note(
            id = UUID.randomUUID(),
            title = title,
            content = content,
            createdAt = now,
            updatedAt = null,
            tags = mutableSetOf(tag)
        )
        val saved = noteRepository.save(noteDTO)

        noteEventRepository.save(
            NoteEvent(
                id = UUID.randomUUID(),
                note = saved,
                eventType = NoteEventType.CREATED,
                createdAt = now,
                updatedAt = null
            )
        )

        return saved.toDTO()
    }

    fun updateNote(noteId: UUID, request: CreateNoteRequest): NoteDTO {
        val note = noteRepository.findById(noteId).orElseThrow {
            ResourceNotFoundException("Note with id $noteId not found")
        }
        if (request.tagName != null) {
            val tag = tagRepository.findByName(request.tagName)
                ?: throw ResourceNotFoundException("Tag with name ${request.tagName} not found")
            note.tags.add(tag)
        }
        note.title = request.title
        note.content = request.content
        note.updatedAt = Instant.now()

        val updatedNote = noteRepository.save(note)
        noteEventRepository.save(
            NoteEvent(
                id = UUID.randomUUID(),
                note = updatedNote,
                eventType = NoteEventType.UPDATED,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )

        return updatedNote.toDTO()
    }

    fun deleteNote(noteId: UUID) {
        noteRepository.findById(noteId)
            ?: throw ResourceNotFoundException("Note with id $noteId not found")

        noteRepository.deleteById(noteId)
    }

    fun pinNote(noteId: UUID): NoteDTO {
        val now = Instant.now()
        val note = noteRepository.findById(noteId).orElseThrow {
            ResourceNotFoundException("Note with id $noteId not found")
        }

        val updatedNote = noteRepository.save(Note(
            id = note.id,
            title = note.title,
            content = note.content,
            isPinned = true,
            createdAt = note.createdAt,
            updatedAt = now,
            tags = note.tags,
            noteEvents = note.noteEvents
        ))
        noteEventRepository.save(
            NoteEvent(
                id = UUID.randomUUID(),
                note = updatedNote,
                eventType = NoteEventType.PINNED,
                createdAt = now,
                updatedAt = now
            )
        )

        return updatedNote.toDTO()
    }
}
