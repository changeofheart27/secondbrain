package com.hieuduongmanh.secondbrain.controller

import com.hieuduongmanh.secondbrain.dto.request.CreateNoteRequest
import com.hieuduongmanh.secondbrain.dto.response.ApiResponse
import com.hieuduongmanh.secondbrain.dto.NoteDTO
import com.hieuduongmanh.secondbrain.service.NoteService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("api/v1/notes")
class NoteController(
    private val noteService: NoteService
) {
    @GetMapping
    fun getAllNotes(): ApiResponse<List<NoteDTO>> {
        val notes = noteService.getAllNotes()
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.OK.value(),
            message = "Get All Notes Successful",
            data = notes
        )
    }

    @GetMapping("/{id}")
    fun getNoteById(@PathVariable id: UUID): ApiResponse<NoteDTO> {
        val note = noteService.getNoteById(noteId = id)
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.OK.value(),
            message = "Get Note By ID Successful",
            data = note
        )
    }

    @GetMapping("/tags/{tagId}")
    fun getNotesByTagId(@PathVariable tagId: UUID): ApiResponse<List<NoteDTO>> {
        val note = noteService.getNotesByTagId(tagId = tagId)
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.OK.value(),
            message = "Get Notes By Tag ID Successful",
            data = note
        )
    }

    @PostMapping
    fun createNote(@Valid @RequestBody request: CreateNoteRequest): ApiResponse<NoteDTO> {
        val newNote = noteService.createNote(
            title = request.title,
            content = request.content,
            tagName = request.tagName
        )
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.CREATED.value(),
            message = "Create Note Successful",
            data = newNote
        )
    }

    @PutMapping("/{noteId}")
    fun updateNote(@PathVariable noteId: UUID, @Valid @RequestBody request: CreateNoteRequest): ApiResponse<NoteDTO> {
        val updatedNote = noteService.updateNote(noteId, request)
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.OK.value(),
            message = "Update Note Successful",
            data = updatedNote
        )
    }

    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id: UUID): ApiResponse<Unit> {
        noteService.deleteNote(noteId = id)
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.NO_CONTENT.value(),
            message = "Delete Note Successful",
            data = null
        )
    }

    @PostMapping("/{id}/pin")
    fun pinNote(@PathVariable id: UUID): ApiResponse<NoteDTO> {
        val pinnedNote = noteService.pinNote(id)
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.OK.value(),
            message = "Pin Note Successful",
            data = pinnedNote
        )
    }
}
