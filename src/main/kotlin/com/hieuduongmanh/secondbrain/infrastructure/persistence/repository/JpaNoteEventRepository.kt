package com.hieuduongmanh.secondbrain.infrastructure.persistence.repository

import com.hieuduongmanh.secondbrain.domain.model.NoteEvent
import com.hieuduongmanh.secondbrain.domain.repository.NoteEventRepository
import com.hieuduongmanh.secondbrain.infrastructure.persistence.mapper.toDomain
import com.hieuduongmanh.secondbrain.infrastructure.persistence.mapper.toEntity

import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class JpaNoteEventRepository(
    private val noteEventEntityRepository: JpaNoteEventRepositorySpringData,
    private val noteEntityRepository : JpaNoteRepositorySpringData
) : NoteEventRepository {

    override fun findAllByNoteId(noteId: UUID): List<NoteEvent> =
        noteEventEntityRepository.findAllByNoteId(noteId)

    override fun save(event: NoteEvent) {
        val noteEntity = noteEntityRepository.findById(event.noteId).orElse(null)
        val noteEventEntity = event.toEntity(noteEntity)
        noteEventEntityRepository.save(noteEventEntity).toDomain()
    }

}
