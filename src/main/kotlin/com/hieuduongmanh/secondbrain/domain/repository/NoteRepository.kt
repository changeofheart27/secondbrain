package com.hieuduongmanh.secondbrain.domain.repository

import com.hieuduongmanh.secondbrain.domain.model.Note
import com.hieuduongmanh.secondbrain.domain.model.Tag
import java.util.UUID

interface NoteRepository {
    fun findAll(): List<Note>
    fun findById(id: UUID): Note?
    fun findByTagsId(tagId: UUID): List<Note>
    fun save(note: Note): Note
    fun delete(noteId: UUID)
}