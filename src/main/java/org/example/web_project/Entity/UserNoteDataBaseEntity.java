package org.example.web_project.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_note")
public class UserNoteDataBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "note_id")
    private NoteDataBaseEntity note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersDBEntity user;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
