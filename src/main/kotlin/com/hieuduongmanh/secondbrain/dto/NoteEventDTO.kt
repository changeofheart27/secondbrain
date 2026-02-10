package com.hieuduongmanh.secondbrain.dto

import com.hieuduongmanh.secondbrain.entity.NoteEventType
import java.time.Instant
import java.util.UUID

data class NoteEventDTO(
    val id: UUID,
    val noteId: UUID,
    val eventType: NoteEventType,
    val createdAt: Instant,
    val updatedAt: Instant?
)
