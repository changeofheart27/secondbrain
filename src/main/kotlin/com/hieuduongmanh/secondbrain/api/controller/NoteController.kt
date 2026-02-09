package com.hieuduongmanh.secondbrain.api.controller

import com.hieuduongmanh.secondbrain.api.request.CreateNoteRequest
import com.hieuduongmanh.secondbrain.api.response.ApiResponse
import com.hieuduongmanh.secondbrain.domain.model.Note
import com.hieuduongmanh.secondbrain.domain.service.NoteService
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
    fun getAllNotes(): ApiResponse<List<Note>> {
        val notes = noteService.getAllNotes()
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.OK.value(),
            message = "Get All Notes Successful",
            data = notes
        )
    }

    @GetMapping("/{id}")
    fun getNoteById(@PathVariable id: UUID): ApiResponse<Note> {
        val note = noteService.getNoteById(noteId = id)
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.OK.value(),
            message = "Get Note By ID Successful",
            data = note
        )
    }

    @PostMapping
    fun createNote(@Valid @RequestBody request: CreateNoteRequest): ApiResponse<Note> {
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

    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id: UUID) = noteService.deleteNote(noteId = id)

    @PostMapping("/{id}/pin")
    fun pinNote(@PathVariable id: UUID): ApiResponse<Note> {
        val pinnedNote = noteService.pinNote(id)
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.OK.value(),
            message = "Pin Note Successful",
            data = pinnedNote
        )
    }
}
