package com.hieuduongmanh.secondbrain.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "note_events")
class NoteEvent(
    @Id
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "note_id", nullable = false)
    val note: Note,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val eventType: NoteEventType,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant,

    @Column(name = "updated_at", nullable = true)
    var updatedAt: Instant?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NoteEvent) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "NoteEventEntity(id=$id, note=$note, eventType=$eventType, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}
