package com.hieuduongmanh.secondbrain.infrastructure.persistence.mapper

import com.hieuduongmanh.secondbrain.domain.model.Note
import com.hieuduongmanh.secondbrain.infrastructure.persistence.entity.NoteEntity

fun NoteEntity.toDomain() = Note(
    id = id,
    title = title,
    content = content,
    tags = tags.map { it.toDomain() }.toSet(),
    isPinned = isPinned,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Note.toEntity() = NoteEntity(
    id = id,
    title = title,
    content = content,
    isPinned = isPinned,
    createdAt = createdAt,
    updatedAt = updatedAt,
    tags = tags.map { it.toEntity() }.toSet()
)
