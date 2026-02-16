package com.hieuduongmanh.secondbrain.controller

import com.hieuduongmanh.secondbrain.dto.request.CreateTagRequest
import com.hieuduongmanh.secondbrain.dto.response.ApiResponse
import com.hieuduongmanh.secondbrain.dto.TagDTO
import com.hieuduongmanh.secondbrain.service.TagService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("api/v1/tags")
class TagController(
    private val tagService: TagService
) {
    @GetMapping
    fun getAllTags(): ApiResponse<List<TagDTO>> {
        val tags = tagService.getAllTags()
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.OK.value(),
            message = "Get All Tags Successful",
            data = tags
        )
    }

    @GetMapping("/{id}")
    fun getTagById(@PathVariable id: UUID): ApiResponse<TagDTO> {
        val tag = tagService.getTagById(id = id)
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.OK.value(),
            message = "Get Tag By ID Successful",
            data = tag
        )
    }

    @GetMapping("/name")
    fun getTagByName(@RequestParam tagName: String): ApiResponse<TagDTO> {
        val tag = tagService.getTagByName(tagName = tagName)
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.OK.value(),
            message = "Get Tag By Name Successful",
            data = tag
        )
    }

    @PostMapping
    fun createTag(@Valid @RequestBody request: CreateTagRequest): ApiResponse<TagDTO> {
        val newTag = tagService.createTag(tagName = request.name)
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.CREATED.value(),
            message = "Create Tag Successful",
            data = newTag
        )
    }

    @DeleteMapping("/{id}")
    fun deleteTag(@PathVariable id: UUID): ApiResponse<Unit> {
        tagService.deleteTag(id)
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.NO_CONTENT.value(),
            message = "Delete Tag Successful",
            data = null
        )
    }
}
