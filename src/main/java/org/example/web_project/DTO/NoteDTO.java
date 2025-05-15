package org.example.web_project.DTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

public class NoteDTO {
    private Long id;
    private String note;
    private LocalDateTime createTime;


    public NoteDTO(Long id, String note, LocalDateTime createTime) {
        this.id = id;
        this.note = note;
        this.createTime = createTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public String getNote() {
        return note;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Note:" + id + " " + note + " " + createTime;
    }
}
