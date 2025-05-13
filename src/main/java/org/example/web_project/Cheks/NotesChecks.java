package org.example.web_project.Cheks;

import org.example.web_project.Entity.NoteDBEntity;
import org.example.web_project.Exceptions.EmptyRequest;
import org.springframework.stereotype.Component;

@Component
public class NotesChecks {

    public void noteFieldCheck (NoteDBEntity entity) {
        if (entity.getNote() == null || entity.getNote().isEmpty()) {
            throw new EmptyRequest("Please, fill in all the fields");
        }
    }
}
