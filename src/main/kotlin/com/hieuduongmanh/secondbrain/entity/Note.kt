package com.hieuduongmanh.secondbrain.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "notes")
class Note(
    @Id
    val id: UUID = UUID.randomUUID(),

    var title: String?,

    @Column(nullable = false)
    var content: String,

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
    val tags: MutableSet<Tag> = mutableSetOf(),

    @OneToMany(mappedBy = "note", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val noteEvents: MutableList<NoteEvent> = mutableListOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Note) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "NoteEntity(id=$id, title=$title, content='$content', isPinned=$isPinned, createdAt=$createdAt, updatedAt=$updatedAt, tags=$tags, noteEvents=$noteEvents)"
    }
}
