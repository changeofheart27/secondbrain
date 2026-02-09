package com.hieuduongmanh.secondbrain.domain.repository

import com.hieuduongmanh.secondbrain.domain.model.NoteEvent
import java.util.UUID

interface NoteEventRepository {
    fun findAllByNoteId(noteId: UUID): List<NoteEvent>
    fun save(event: NoteEvent)
}