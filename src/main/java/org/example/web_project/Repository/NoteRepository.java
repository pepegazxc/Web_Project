package org.example.web_project.Repository;

import org.example.web_project.Cheks.NotesChecks;
import org.example.web_project.Entity.NoteDBEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

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
}
