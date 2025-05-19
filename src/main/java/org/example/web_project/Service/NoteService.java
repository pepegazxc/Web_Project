package org.example.web_project.Service;

import org.example.web_project.DTO.NoteDTO;
import org.example.web_project.Entity.NoteDataBaseEntity;
import org.example.web_project.Repository.NoteRepository;
import org.example.web_project.Repository.UserNoteRepository;
import org.example.web_project.Repository.UserTokenRepository;
import org.example.web_project.SessionStorage.UserSessionStorage;
import org.example.web_project.DTO.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserSessionStorage userSessionStorage;
    private final UserNoteRepository userNoteRepository;
    private final UserTokenRepository userTokenRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository, UserSessionStorage userSessionStorage, UserNoteRepository userNoteRepository, UserTokenRepository userTokenRepository) {
        this.noteRepository = noteRepository;
        this.userSessionStorage = userSessionStorage;
        this.userNoteRepository = userNoteRepository;
        this.userTokenRepository = userTokenRepository;
    }

    public void addNewNote(UserRequest userRequest) {
        Long userID = userSessionStorage.getUserID();
        String token = userSessionStorage.getToken();

        userTokenRepository.checkToken(token, userID);
        NoteDataBaseEntity noteDB = new NoteDataBaseEntity.Builder()
                .note(userRequest.getNote())
                .build();

        Long noteID = noteRepository.addNewNote(noteDB);

        userNoteRepository.addUserAndNoteID(userID, noteID);
    }

    public List<NoteDTO> showAllNotesWithData() {
        Long userID = userSessionStorage.getUserID();
        String token = userSessionStorage.getToken();

        userTokenRepository.checkToken(token, userID);

        return noteRepository.getAllNotesWithData(userID);
    }

    public void deleteAllUserNotes () {
        Long userID = userSessionStorage.getUserID();
        String token = userSessionStorage.getToken();

        userTokenRepository.checkToken(token, userID);

        noteRepository.deleteAllUserNotes(userID);
    }

    public void deleteChosenNote(UserRequest userRequest) {
        Long userID = userSessionStorage.getUserID();
        String token = userSessionStorage.getToken();

        userTokenRepository.checkToken(token, userID);
        NoteDataBaseEntity noteDBEntity = new NoteDataBaseEntity.Builder()
                .id(userRequest.getId())
                .build();

        noteRepository.deleteChosenNotes(userID, noteDBEntity);

    }

    public void updateChosenNote(UserRequest userRequest) {
        Long userID = userSessionStorage.getUserID();
        String token = userSessionStorage.getToken();

        userTokenRepository.checkToken(token, userID);

        NoteDataBaseEntity noteDBEntity = new NoteDataBaseEntity.Builder()
                .note(userRequest.getNote())
                .id(userRequest.getId())
                .build();

        noteRepository.updateChosenNote(noteDBEntity, userID);
    }
}
