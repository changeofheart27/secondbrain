package com.hieuduongmanh.secondbrain.dto

import java.time.Instant
import java.util.UUID

data class NoteDTO(
    val id: UUID,
    val title: String?,
    val content: String,
    val tagDTOs: MutableSet<TagDTO>? = mutableSetOf(),
    val isPinned: Boolean = false,
    val createdAt: Instant,
    val updatedAt: Instant?,
    val noteEventDTOs: MutableList<NoteEventDTO>? = mutableListOf()
)
