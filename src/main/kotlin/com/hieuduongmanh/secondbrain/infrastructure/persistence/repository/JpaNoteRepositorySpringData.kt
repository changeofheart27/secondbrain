package com.hieuduongmanh.secondbrain.infrastructure.persistence.repository

import com.hieuduongmanh.secondbrain.infrastructure.persistence.entity.NoteEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaNoteRepositorySpringData : JpaRepository<NoteEntity, UUID> {
    fun findByTagsId(tagId: UUID): List<NoteEntity>
}
