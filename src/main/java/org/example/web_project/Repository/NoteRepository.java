package org.example.web_project.Repository;

import org.example.web_project.Cheks.NotesChecks;
import org.example.web_project.DTO.NoteDTO;
import org.example.web_project.Entity.NoteDataBaseEntity;
import org.example.web_project.Exceptions.NoAccessToEditing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.*;

@Repository
public class NoteRepository {
    private static final String SQL_INSERT_NEW_NOTE =
            "INSERT INTO notes(note) VALUES (?)";

    private static final String SQL_DELETE_USER_ID_AND_NOTE_ID_BY_NOTE_ID =
            "DELETE FROM user_note WHERE note_id = ?";

    private static final String SQL_DELETE_NOTE_BY_ID =
            "DELETE FROM notes WHERE id = ?";

    private static final String SQL_UPDATE_CHOSEN_TEXT =
            "UPDATE notes SET note = ? WHERE id = ?";

    private static final String SQL_DElETE_USER_ID_AND_NOTE_ID_BY_USER_ID =
            "DELETE FROM user_note WHERE user_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final NotesChecks notesChecks;

    @Autowired
    public NoteRepository(JdbcTemplate jdbcTemplate, NotesChecks notesChecks) {
        this.jdbcTemplate = jdbcTemplate;
        this.notesChecks = notesChecks;
    }

    public Long addNewNote(NoteDataBaseEntity noteDBEntity) {
        notesChecks.noteFieldCheck(noteDBEntity);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT_NEW_NOTE, new String[]{"id"});
            ps.setString(1, noteDBEntity.getNote());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<NoteDTO> getAllNotesWithData(Long userID){
        List<Long> notesIds = addNoteIdsToList(userID);

        String inSql = String.join(",", Collections.nCopies(notesIds.size(), "?"));
        String query = "SELECT * FROM notes WHERE id IN (" + inSql + ")";

        return jdbcTemplate.query(query,
                notesIds.toArray(),
                (rs, rowNum) ->
            new NoteDTO(
                    rs.getLong("id"),
                    rs.getString("note"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            )
        );
    }

    public String deleteAllUserNotes(Long userID){
        List<Long> notesIds = addNoteIdsToList(userID);

        jdbcTemplate.update(SQL_DElETE_USER_ID_AND_NOTE_ID_BY_USER_ID, userID);

        for (Long noteId : notesIds) {
            jdbcTemplate.update("DELETE FROM notes WHERE id = ?", noteId);
        }

        return "All notes have been deleted!";
    }

    public String deleteChosenNotes(Long userID, NoteDataBaseEntity noteDBEntity) {
        notesChecks.checkingForIdOfNote(noteDBEntity);

        if(!isNoteBelongsToUser(noteDBEntity.getId(), userID)) {
            throw new NoAccessToEditing("You are not allowed to delete this note!");
        }

        jdbcTemplate.update(SQL_DELETE_USER_ID_AND_NOTE_ID_BY_NOTE_ID, noteDBEntity.getId());
        jdbcTemplate.update(SQL_DELETE_NOTE_BY_ID, noteDBEntity.getId());

        return "Chosen notes have been deleted!";
    }

    public String updateChosenNote(NoteDataBaseEntity noteDBEntity, Long userID) {
        notesChecks.checkingForIdOfNote(noteDBEntity);
        notesChecks.noteFieldCheck(noteDBEntity);

        if (!isNoteBelongsToUser(noteDBEntity.getId(), userID)) {
            throw new NoAccessToEditing("You are not allowed to update this note!");
        }

        jdbcTemplate.update(SQL_UPDATE_CHOSEN_TEXT, noteDBEntity.getNote(), noteDBEntity.getId());

        return "Chosen notes have been updated!";
    }

    private List<Long> addNoteIdsToList(Long userID){
        List<Long> notesIds = jdbcTemplate.queryForList("select note_id from user_note where user_id = ?",
                Long.class,
                userID);

        notesChecks.checkingForNoteIdsInList(notesIds);

        return  notesIds;
    }


    private boolean isNoteBelongsToUser(Long noteId, Long userId) {
        String checkOwnership = """
        SELECT COUNT(*) FROM user_note WHERE note_id = ? AND user_id = ?
    """;
        Integer count = jdbcTemplate.queryForObject(checkOwnership, Integer.class, noteId, userId);
        return count != null && count > 0;
    }
}
