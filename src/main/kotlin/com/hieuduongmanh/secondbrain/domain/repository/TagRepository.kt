package com.hieuduongmanh.secondbrain.domain.repository

import com.hieuduongmanh.secondbrain.domain.model.Tag
import java.util.UUID

interface TagRepository {
    fun findAll(): List<Tag>
    fun findById(id: UUID): Tag?
    fun findByName(tagName: String?): Tag?
    fun save(tag: Tag): Tag
    fun delete(id: UUID)
}