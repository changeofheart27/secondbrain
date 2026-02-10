package com.hieuduongmanh.secondbrain.repository

import com.hieuduongmanh.secondbrain.entity.Note
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NoteRepository : JpaRepository<Note, UUID> {
    fun findByTagsId(tagId: UUID): List<Note>
}
