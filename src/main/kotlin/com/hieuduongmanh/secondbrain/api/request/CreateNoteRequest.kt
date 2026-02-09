package com.hieuduongmanh.secondbrain.api.request

import jakarta.validation.constraints.NotBlank

data class CreateNoteRequest(
    val title: String?,

    @field:NotBlank
    val content: String,

    val tagName: String?
)
