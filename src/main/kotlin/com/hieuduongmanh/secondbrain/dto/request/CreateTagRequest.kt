package com.hieuduongmanh.secondbrain.dto.request

import jakarta.validation.constraints.NotBlank

data class CreateTagRequest(
    @field:NotBlank
    val name: String
)
