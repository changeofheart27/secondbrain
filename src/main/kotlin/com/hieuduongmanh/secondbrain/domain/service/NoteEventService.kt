package com.hieuduongmanh.secondbrain.domain.service

import com.hieuduongmanh.secondbrain.domain.model.NoteEvent
import com.hieuduongmanh.secondbrain.domain.repository.NoteEventRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class NoteEventService(
    private val noteEventRepository: NoteEventRepository
) {
    fun getNoteEventsByNoteId(noteId: UUID): List<NoteEvent> =
        noteEventRepository.findAllByNoteId(noteId)
}