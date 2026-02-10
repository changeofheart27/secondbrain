package com.hieuduongmanh.secondbrain.controller

import com.hieuduongmanh.secondbrain.dto.NoteEventDTO
import com.hieuduongmanh.secondbrain.dto.response.ApiResponse
import com.hieuduongmanh.secondbrain.service.NoteEventService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("api/v1/note-events")
class NoteEventController(
    private val noteEventService: NoteEventService
) {
    @GetMapping("/{noteId}")
    fun getNoteEventsByNoteId(@PathVariable noteId: UUID): ApiResponse<List<NoteEventDTO>> {
        val noteEvents = noteEventService.getNoteEventsByNoteId(noteId = noteId)
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.OK.value(),
            message = "Get NoteEvents By Note ID Successful",
            data = noteEvents
        )
    }
}