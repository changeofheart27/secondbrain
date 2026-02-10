package com.hieuduongmanh.secondbrain.dto.mapper

import com.hieuduongmanh.secondbrain.dto.NoteDTO
import com.hieuduongmanh.secondbrain.entity.Note
import com.hieuduongmanh.secondbrain.entity.NoteEvent

fun Note.toDTO() = NoteDTO(
    id = id,
    title = title,
    content = content,
    tagDTOs = tags.map { it.toDTO() }.toMutableSet(),
    isPinned = isPinned,
    createdAt = createdAt,
    updatedAt = updatedAt,
    noteEventDTOs = noteEvents.map { it.toDTO() }.toMutableList()
)

fun NoteDTO.toEntity(noteEvents: MutableList<NoteEvent>) = Note(
    id = id,
    title = title,
    content = content,
    tags = tagDTOs?.map { it.toEntity() }?.toMutableSet() ?: mutableSetOf(),
    isPinned = isPinned,
    createdAt = createdAt,
    updatedAt = updatedAt,
    noteEvents = noteEvents
)
