package org.example.web_project.Cheks;

import org.example.web_project.Entity.NoteDBEntity;
import org.example.web_project.Exceptions.EmptyID;
import org.example.web_project.Exceptions.EmptyRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotesChecks {

    public void noteFieldCheck (NoteDBEntity entity) {
        if (entity.getNote() == null || entity.getNote().isEmpty()) {
            throw new EmptyRequest("Please, fill in all the fields");
        }
    }

    public void checkingForNoteIdsInList(List<Long> notesIds){
        if (notesIds == null || notesIds.isEmpty()) {
            throw new EmptyID("Ids notes list is empty");
        }
    }

    public void checkingForIdOfNote(NoteDBEntity entity){
        if (entity.getId() == null) {
            throw new EmptyRequest("Id of note is empty");
        }
    }
}
