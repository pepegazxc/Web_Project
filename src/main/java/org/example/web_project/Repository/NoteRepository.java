package org.example.web_project.Repository;

import org.example.web_project.Cheks.NotesChecks;
import org.example.web_project.DTO.NoteDTO;
import org.example.web_project.Entity.NoteDBEntity;
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

    private final JdbcTemplate jdbcTemplate;
    private final NotesChecks notesChecks;

    @Autowired
    public NoteRepository(JdbcTemplate jdbcTemplate, NotesChecks notesChecks) {
        this.jdbcTemplate = jdbcTemplate;
        this.notesChecks = notesChecks;
    }

    public Long addNewNote(NoteDBEntity noteDBEntity) {
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


}
