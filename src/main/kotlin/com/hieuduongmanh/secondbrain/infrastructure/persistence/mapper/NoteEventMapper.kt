package com.hieuduongmanh.secondbrain.infrastructure.persistence.mapper

import com.hieuduongmanh.secondbrain.domain.model.NoteEvent
import com.hieuduongmanh.secondbrain.infrastructure.persistence.entity.NoteEntity
import com.hieuduongmanh.secondbrain.infrastructure.persistence.entity.NoteEventEntity

fun NoteEventEntity.toDomain() = NoteEvent(
    id = id,
    noteId = note.id,
    eventType = eventType,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun NoteEvent.toEntity(note: NoteEntity) = NoteEventEntity(
    id = id,
    note = note,
    eventType = eventType,
    createdAt = createdAt,
    updatedAt = updatedAt
)
