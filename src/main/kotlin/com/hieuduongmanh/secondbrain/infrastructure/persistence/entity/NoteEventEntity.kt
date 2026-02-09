package com.hieuduongmanh.secondbrain.infrastructure.persistence.entity

import com.hieuduongmanh.secondbrain.domain.model.NoteEventType
import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "note_events")
class NoteEventEntity(
    @Id
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "note_id", nullable = false)
    val note: NoteEntity,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val eventType: NoteEventType,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant,

    @Column(name = "updated_at", nullable = true)
    var updatedAt: Instant?
)
