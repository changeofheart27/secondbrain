package com.hieuduongmanh.secondbrain.dto

import java.time.Instant
import java.util.UUID

data class TagDTO(
    val id: UUID,
    val name: String,
    val createdAt: Instant
)
