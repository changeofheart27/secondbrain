package com.hieuduongmanh.secondbrain.dto.mapper

import com.hieuduongmanh.secondbrain.dto.NoteEventDTO
import com.hieuduongmanh.secondbrain.entity.Note
import com.hieuduongmanh.secondbrain.entity.NoteEvent

fun NoteEvent.toDTO() = NoteEventDTO(
    id = id,
    noteId = note.id,
    eventType = eventType,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun NoteEventDTO.toEntity(note: Note) = NoteEvent(
    id = id,
    note = note,
    eventType = eventType,
    createdAt = createdAt,
    updatedAt = updatedAt
)
