package com.hieuduongmanh.secondbrain.api.controller

import com.hieuduongmanh.secondbrain.api.request.CreateTagRequest
import com.hieuduongmanh.secondbrain.api.response.ApiResponse
import com.hieuduongmanh.secondbrain.domain.model.Tag
import com.hieuduongmanh.secondbrain.domain.service.TagService
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
    fun getAllTags(): ApiResponse<List<Tag>> {
        val tags = tagService.getAllTags()
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.OK.value(),
            message = "Get All Tags Successful",
            data = tags
        )
    }

    @GetMapping("/{id}")
    fun getTagById(@PathVariable id: UUID): ApiResponse<Tag> {
        val tag = tagService.getTagById(id = id)
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.OK.value(),
            message = "Get Tag By ID Successful",
            data = tag
        )
    }

    @GetMapping("/{tagName}")
    fun getTagByName(@PathVariable tagName: String): ApiResponse<Tag> {
        val tag = tagService.getTagByName(tagName = tagName)
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.OK.value(),
            message = "Get Tag By Name Successful",
            data = tag
        )
    }

    @PostMapping
    fun createTag(@Valid @RequestBody request: CreateTagRequest): ApiResponse<Tag> {
        val newTag = tagService.createTag(tagName = request.name)
        return ApiResponse(
            timestamp = Instant.now(),
            status = HttpStatus.CREATED.value(),
            message = "Create Tag Successful",
            data = newTag
        )
    }
}
