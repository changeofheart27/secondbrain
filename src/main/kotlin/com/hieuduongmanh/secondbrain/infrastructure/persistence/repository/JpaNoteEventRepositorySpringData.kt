package com.hieuduongmanh.secondbrain.infrastructure.persistence.repository

import com.hieuduongmanh.secondbrain.domain.model.NoteEvent
import com.hieuduongmanh.secondbrain.infrastructure.persistence.entity.NoteEventEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaNoteEventRepositorySpringData : JpaRepository<NoteEventEntity, UUID> {
    fun findAllByNoteId(noteId: UUID): List<NoteEvent>
}
