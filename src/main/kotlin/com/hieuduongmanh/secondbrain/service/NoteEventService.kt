package com.hieuduongmanh.secondbrain.service

import com.hieuduongmanh.secondbrain.dto.NoteEventDTO
import com.hieuduongmanh.secondbrain.dto.mapper.toDTO
import com.hieuduongmanh.secondbrain.repository.NoteEventRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class NoteEventService(
    private val noteEventRepository: NoteEventRepository
) {
    fun getNoteEventsByNoteId(noteId: UUID): List<NoteEventDTO> =
        noteEventRepository.findAllByNoteId(noteId).map { it.toDTO() }
}