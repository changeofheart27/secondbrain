package com.hieuduongmanh.secondbrain.infrastructure.persistence.repository

import com.hieuduongmanh.secondbrain.infrastructure.persistence.entity.TagEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaTagRepositorySpringData : JpaRepository<TagEntity, UUID> {
    fun findByName(tagName: String?): TagEntity?
}
