package com.hieuduongmanh.secondbrain.dto.mapper

import com.hieuduongmanh.secondbrain.dto.TagDTO
import com.hieuduongmanh.secondbrain.entity.Tag

fun Tag.toDTO() = TagDTO(
    id = id,
    name = name,
    createdAt = createdAt
)

fun TagDTO.toEntity() = Tag(
    id = id,
    name = name,
    createdAt = createdAt
)
