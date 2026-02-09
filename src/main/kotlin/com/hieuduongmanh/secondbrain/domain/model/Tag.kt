package com.hieuduongmanh.secondbrain.domain.model

import java.time.Instant
import java.util.UUID

data class Tag(
    val id: UUID,
    val name: String,
    val createdAt: Instant
)
