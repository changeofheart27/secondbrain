package com.hieuduongmanh.secondbrain.domain.service

import com.hieuduongmanh.secondbrain.api.exception.ResourceNotFoundException
import com.hieuduongmanh.secondbrain.domain.model.Tag
import com.hieuduongmanh.secondbrain.domain.repository.TagRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class TagService(
    private val tagRepository: TagRepository
) {
    fun getAllTags(): List<Tag> = tagRepository.findAll()

    fun getTagById(id: UUID): Tag = tagRepository.findById(id)
        ?: throw ResourceNotFoundException("Tag with id $id not found")

    fun getTagByName(tagName: String): Tag = tagRepository.findByName(tagName)
        ?: throw ResourceNotFoundException("Tag with name $tagName not found")

    fun createTag(tagName: String): Tag {
        val existing = tagRepository.findByName(tagName)
        if (existing != null) {
            return existing
        }

        val tag = Tag(
            id = UUID.randomUUID(),
            name = tagName.lowercase(),
            createdAt = Instant.now()
        )

        return tagRepository.save(tag)
    }

    fun deleteTag(id: UUID) = tagRepository.delete(id)
}
