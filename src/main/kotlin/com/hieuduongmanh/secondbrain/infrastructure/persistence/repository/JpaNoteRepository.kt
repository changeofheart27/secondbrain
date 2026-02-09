package com.hieuduongmanh.secondbrain.infrastructure.persistence.repository

import com.hieuduongmanh.secondbrain.domain.model.Note
import com.hieuduongmanh.secondbrain.domain.repository.NoteRepository
import com.hieuduongmanh.secondbrain.infrastructure.persistence.mapper.toDomain
import com.hieuduongmanh.secondbrain.infrastructure.persistence.mapper.toEntity

import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class JpaNoteRepository(
    private val noteEntityRepository: JpaNoteRepositorySpringData
) : NoteRepository {
    override fun findAll(): List<Note> {
        return noteEntityRepository.findAll().map { it.toDomain() }
    }

    override fun findById(id: UUID): Note? =
        noteEntityRepository.findById(id).orElse(null)?.toDomain()

    override fun findByTagsId(tagId: UUID): List<Note> =
        noteEntityRepository.findByTagsId(tagId).map { it.toDomain() }

    override fun save(note: Note): Note {
        val noteEntity = note.toEntity()
        return noteEntityRepository.save(noteEntity).toDomain()
    }

    override fun delete(noteId: UUID) = noteEntityRepository.deleteById(noteId)
}
