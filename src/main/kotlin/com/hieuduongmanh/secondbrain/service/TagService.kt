package com.hieuduongmanh.secondbrain.service

import com.hieuduongmanh.secondbrain.dto.TagDTO
import com.hieuduongmanh.secondbrain.dto.mapper.toDTO
import com.hieuduongmanh.secondbrain.entity.Tag
import com.hieuduongmanh.secondbrain.exception.ResourceNotFoundException
import com.hieuduongmanh.secondbrain.repository.TagRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class TagService(
    private val tagRepository: TagRepository
) {
    fun getAllTags(): List<TagDTO> = tagRepository.findAll().map { it.toDTO() }

    fun getTagById(id: UUID): TagDTO {
        val tag = tagRepository.findById(id).orElseThrow {
            ResourceNotFoundException("Tag with id $id not found")
        }
        return tag.toDTO()
    }

    fun getTagByName(tagName: String): TagDTO {
        val tag = tagRepository.findByName(tagName)
            ?: throw ResourceNotFoundException("Tag with name $tagName not found")
        return tag.toDTO()
    }

    fun createTag(tagName: String): TagDTO {
        val existing = tagRepository.findByName(tagName)
        if (existing != null) {
            return existing.toDTO()
        }

        val tag = Tag(
            id = UUID.randomUUID(),
            name = tagName.lowercase(),
            createdAt = Instant.now()
        )
        tagRepository.save(tag)

        return tag.toDTO()
    }

    fun deleteTag(id: UUID) = tagRepository.deleteById(id)
}
