package com.hieuduongmanh.secondbrain.repository

import com.hieuduongmanh.secondbrain.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TagRepository : JpaRepository<Tag, UUID> {
    fun findByName(tagName: String?): Tag?
}
