package com.hieuduongmanh.secondbrain.domain.model

import java.time.Instant
import java.util.UUID

data class NoteEvent(
    val id: UUID,
    val noteId: UUID,
    val eventType: NoteEventType,
    val createdAt: Instant,
    val updatedAt: Instant?
)

enum class NoteEventType {
    CREATED,
    UPDATED,
    DELETED,
    PINNED,
    UNPINNED
}