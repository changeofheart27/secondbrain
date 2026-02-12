package com.hieuduongmanh.secondbrain.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.assertThrows
import com.hieuduongmanh.secondbrain.entity.Tag
import com.hieuduongmanh.secondbrain.dto.TagDTO
import com.hieuduongmanh.secondbrain.exception.ResourceNotFoundException
import com.hieuduongmanh.secondbrain.repository.TagRepository
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.assertEquals
import kotlin.test.assertTrue

import java.time.Instant
import java.util.Optional
import java.util.UUID

@ExtendWith(MockKExtension::class)
class TagServiceTest {
    @MockK
    private lateinit var tagRepository: TagRepository

    private lateinit var tagService: TagService

    @BeforeEach
    fun setUp() {
        tagService = TagService(tagRepository)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getAllTags should return all tags`() {
        val tags = listOf(
            Tag(UUID.randomUUID(), "Tag1", Instant.now()),
            Tag(UUID.randomUUID(), "Tag2", Instant.now())
        )
        every { tagRepository.findAll() } returns tags

        val result = tagService.getAllTags()

        assertEquals(2, result.size)
        assertTrue(result[0] is TagDTO)
        verify { tagRepository.findAll() }
    }

    @Test
    fun `getTagById should return the tag when found`() {
        val tagId = UUID.randomUUID()
        val tag = Tag(tagId, "Tag1", Instant.now())
        every { tagRepository.findById(tagId) } returns Optional.of(tag)

        val result = tagService.getTagById(tagId)

        assertEquals("Tag1", result.name)
        verify { tagRepository.findById(tagId) }
    }

    @Test
    fun `getTagById should throw ResourceNotFoundException when tag not found`() {
        val tagId = UUID.randomUUID()
        every { tagRepository.findById(tagId) } returns Optional.empty()

        val exception = assertThrows<ResourceNotFoundException> {
            tagService.getTagById(tagId)
        }

        assertEquals("Tag with id $tagId not found", exception.message)
        verify { tagRepository.findById(tagId) }
    }

    @Test
    fun `getTagByName should return the tag when found`() {
        val tagName = "Tag1"
        val tag = Tag(UUID.randomUUID(), tagName, Instant.now())
        every { tagRepository.findByName(tagName) } returns tag

        val result = tagService.getTagByName(tagName)

        assertEquals(tagName, result.name)
        verify { tagRepository.findByName(tagName) }
    }

    @Test
    fun `getTagByName should throw ResourceNotFoundException when tag not found`() {
        val tagName = "Tag1"
        every { tagRepository.findByName(tagName) } returns null

        val exception = assertThrows<ResourceNotFoundException> {
            tagService.getTagByName(tagName)
        }

        assertEquals("Tag with name $tagName not found", exception.message)
        verify { tagRepository.findByName(tagName) }
    }

    @Test
    fun `createTag should create a new tag when tag does not exist`() {
        val tagName = "NewTag"
        every { tagRepository.findByName(tagName) } returns null
        every { tagRepository.save(any()) } answers { firstArg() }

        val result = tagService.createTag(tagName)

        assertEquals(tagName.lowercase(), result.name)
        verify { tagRepository.findByName(tagName) }
        verify { tagRepository.save(any()) }
    }

    @Test
    fun `createTag should return existing tag when tag already exists`() {
        val tagName = "ExistingTag"
        val existingTag = Tag(UUID.randomUUID(), tagName.lowercase(), Instant.now())
        every { tagRepository.findByName(tagName) } returns existingTag

        val result = tagService.createTag(tagName)

        assertEquals(existingTag.id, result.id)
        assertEquals(existingTag.name, result.name)
        verify { tagRepository.findByName(tagName) }
        verify(exactly = 0) { tagRepository.save(any()) }
    }

    @Test
    fun `deleteTag should delete the tag`() {
        val tagId = UUID.randomUUID()
        every { tagRepository.findById(tagId) } returns Optional.of(mockk())
        every { tagRepository.deleteById(tagId) } just Runs

        tagService.deleteTag(tagId)

        verify { tagRepository.deleteById(tagId) }
    }
}
