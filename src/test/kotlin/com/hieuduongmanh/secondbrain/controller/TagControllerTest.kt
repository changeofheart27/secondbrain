package com.hieuduongmanh.secondbrain.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.hieuduongmanh.secondbrain.dto.TagDTO
import com.hieuduongmanh.secondbrain.dto.request.CreateTagRequest
import com.hieuduongmanh.secondbrain.service.TagService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant
import java.util.*

@WebMvcTest(TagController::class)
class TagControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var tagService: TagService

    @Test
    fun `GET all tags should return 200`() {
        val tagDTO1 = TagDTO(
            id = UUID.randomUUID(),
            name = "Tag 1",
            createdAt = Instant.now()
        )
        val tagDTO2 = TagDTO(
            id = UUID.randomUUID(),
            name = "Tag 2",
            createdAt = Instant.now()
        )

        every { tagService.getAllTags() } returns mutableListOf(tagDTO1, tagDTO2)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tags"))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value(tagDTO1.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Get All Tags Successful"))
    }

    @Test
    fun `GET tag by id should return 200`() {
        val tagId = UUID.randomUUID()
        val tagDTO = TagDTO(
            id = tagId,
            name = "Tag",
            createdAt = Instant.now()
        )

        every { tagService.getTagById(tagId) } returns tagDTO

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tags/$tagId"))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(tagDTO.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Get Tag By ID Successful"))
    }

    @Test
    fun `GET tag by name should return 200`() {
        val tagDTO = TagDTO(
            id = UUID.randomUUID(),
            name = "TagName",
            createdAt = Instant.now()
        )

        every { tagService.getTagByName("TagName") } returns tagDTO

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tags/name?tagName=${tagDTO.name}"))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(tagDTO.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Get Tag By Name Successful"))
    }

    @Test
    fun `POST new tag should return 201`() {
        val tagName = "New Tag"
        val newTagDTO = TagDTO(
            id = UUID.randomUUID(),
            name = tagName,
            createdAt = Instant.now()
        )
        val createTagRequest = CreateTagRequest(
            name = tagName
        )

        every { tagService.createTag(any()) } returns newTagDTO

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tags")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(createTagRequest)))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.CREATED.value()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(tagName))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Create Tag Successful"))
    }

    @Test
    fun `DELETE tag should return 204`() {
        val tagId = UUID.randomUUID()

        every { tagService.deleteTag(tagId) } just Runs

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/tags/$tagId"))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.NO_CONTENT.value()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Delete Tag Successful"))
    }
}
