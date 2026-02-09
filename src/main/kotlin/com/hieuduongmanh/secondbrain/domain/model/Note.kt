package com.hieuduongmanh.secondbrain.domain.model

import java.time.Instant
import java.util.UUID

data class Note(
    val id: UUID,
    val title: String?,
    val content: String,
    val tags: Set<Tag> = emptySet(),
    val isPinned: Boolean = false,
    val createdAt: Instant,
    val updatedAt: Instant?,
    val noteEvents: List<NoteEvent> = emptyList()
)
