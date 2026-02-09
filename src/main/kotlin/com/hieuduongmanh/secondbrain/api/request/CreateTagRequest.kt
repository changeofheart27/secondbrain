package com.hieuduongmanh.secondbrain.api.request

import jakarta.validation.constraints.NotBlank

data class CreateTagRequest(
    @field:NotBlank
    val name: String
)
