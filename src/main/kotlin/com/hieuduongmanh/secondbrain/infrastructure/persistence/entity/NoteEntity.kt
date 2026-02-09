package com.hieuduongmanh.secondbrain.infrastructure.persistence.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "notes")
class NoteEntity(
    @Id
    val id: UUID = UUID.randomUUID(),

    val title: String?,

    @Column(nullable = false)
    val content: String,

    @Column(name = "is_pinned", nullable = false)
    var isPinned: Boolean = false,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = true)
    var updatedAt: Instant?,

    @ManyToMany
    @JoinTable(
        name = "notes_tags",
        joinColumns = [JoinColumn(name = "note_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    val tags: Set<TagEntity> = emptySet()
)
