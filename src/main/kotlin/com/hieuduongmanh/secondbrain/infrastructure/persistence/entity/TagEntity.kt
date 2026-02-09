package com.hieuduongmanh.secondbrain.infrastructure.persistence.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "tags")
class TagEntity(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, unique = true)
    val name: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),
)
