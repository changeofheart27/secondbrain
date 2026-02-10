package com.hieuduongmanh.secondbrain.repository

import com.hieuduongmanh.secondbrain.entity.NoteEvent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NoteEventRepository : JpaRepository<NoteEvent, UUID> {
    fun findAllByNoteId(noteId: UUID): List<NoteEvent>
}
