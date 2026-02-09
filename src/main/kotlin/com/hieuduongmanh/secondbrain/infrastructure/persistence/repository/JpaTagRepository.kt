package com.hieuduongmanh.secondbrain.infrastructure.persistence.repository

import com.hieuduongmanh.secondbrain.domain.model.Tag
import com.hieuduongmanh.secondbrain.domain.repository.TagRepository
import com.hieuduongmanh.secondbrain.infrastructure.persistence.mapper.toDomain
import com.hieuduongmanh.secondbrain.infrastructure.persistence.mapper.toEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class JpaTagRepository(
    private val tagEntityRepository: JpaTagRepositorySpringData
) : TagRepository {
    override fun findAll(): List<Tag> {
        return tagEntityRepository.findAll().map { it.toDomain() }
    }

    override fun findById(id: UUID): Tag? =
        tagEntityRepository.findById(id).orElse(null)?.toDomain()

    override fun findByName(tagName: String?): Tag? {
        return tagEntityRepository.findByName(tagName)?.toDomain()
    }

    override fun save(tag: Tag): Tag =
        tagEntityRepository.save(tag.toEntity()).toDomain()

    override fun delete(id: UUID) = tagEntityRepository.deleteById(id)
}
