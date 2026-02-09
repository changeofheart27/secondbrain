package com.hieuduongmanh.secondbrain.infrastructure.persistence.mapper

import com.hieuduongmanh.secondbrain.domain.model.Tag
import com.hieuduongmanh.secondbrain.infrastructure.persistence.entity.TagEntity

fun TagEntity.toDomain() = Tag(
    id = id,
    name = name,
    createdAt = createdAt
)

fun Tag.toEntity() = TagEntity(
    id = id,
    name = name,
    createdAt = createdAt
)
