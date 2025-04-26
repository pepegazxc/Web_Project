package org.example.web_project.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notes")
public class NoteDBEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private String note;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
