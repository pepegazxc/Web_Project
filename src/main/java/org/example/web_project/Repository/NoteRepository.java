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

    private static final
    String SQL_SELECT_NOTE_ID_FROM_USER_NOTE =
            "SELECT note_id FROM user_note WHERE user_id = ?";

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
        List<Long> notesIds = jdbcTemplate.queryForList("select note_id from user_note where user_id = ?",
                Long.class,
                userID);

        notesChecks.checkingForNoteIdsInList(notesIds);

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
        List<Long> notesIds = jdbcTemplate.queryForList(
                SQL_SELECT_NOTE_ID_FROM_USER_NOTE,
                Long.class,
                userID);

        notesChecks.checkingForNoteIdsInList(notesIds);

        String deleteUserIdAndNoteIdByUserId = "DELETE FROM user_note WHERE user_id = ?";
        jdbcTemplate.update(deleteUserIdAndNoteIdByUserId, userID);

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

        String deleteUserIdAndNoteIdByUserId = "DELETE FROM user_note WHERE note_id = ?";
        String deleteNoteById = "DELETE FROM notes WHERE id = ?";

        jdbcTemplate.update(deleteUserIdAndNoteIdByUserId, noteDBEntity.getId());
        jdbcTemplate.update(deleteNoteById, noteDBEntity.getId());

        return "Chosen notes have been deleted!";
    }

    public String updateChosenNote(NoteDataBaseEntity noteDBEntity, Long userID) {
        notesChecks.checkingForIdOfNote(noteDBEntity);
        notesChecks.noteFieldCheck(noteDBEntity);

        if (!isNoteBelongsToUser(noteDBEntity.getId(), userID)) {
            throw new NoAccessToEditing("You are not allowed to update this note!");
        }

        String updateChosenText = "UPDATE notes SET note = ? WHERE id = ?";

        jdbcTemplate.update(updateChosenText, noteDBEntity.getNote(), noteDBEntity.getId());

        return "Chosen notes have been updated!";
    }

    private Map<Long, String> addNotesToMap(Long userID){
        List<Long> notesIds = jdbcTemplate.queryForList("select note_id from user_note where user_id = ?",
                Long.class,
                userID);

        notesChecks.checkingForNoteIdsInList(notesIds);

        String inSql = String.join(",", Collections.nCopies(notesIds.size(), "?"));
        String selectIdUserNotes = "SELECT id,note FROM notes WHERE id IN (" + inSql + ")";

        return jdbcTemplate.query(selectIdUserNotes,
                notesIds.toArray(),
                rs -> {
            Map<Long, String> result = new HashMap<>();
                    while (rs.next()) {
                        result.put(rs.getLong("id"), rs.getString("text"));
                    }
                    return result;
        });
    }

    private boolean isNoteBelongsToUser(Long noteId, Long userId) {
        String SQL_CHECK_OWNERSHIP = """
        SELECT COUNT(*) FROM user_note WHERE note_id = ? AND user_id = ?
    """;
        Integer count = jdbcTemplate.queryForObject(SQL_CHECK_OWNERSHIP, Integer.class, noteId, userId);
        return count != null && count > 0;
    }
}
