package org.example.web_project.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserNoteRepository {
    private static final String SQL_INSERT_USER_NOTE_ID =
            "INSERT INTO user_note(user_id, note_id) VALUES(?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserNoteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addUserAndNoteID(Long userID, Long noteID) {
        jdbcTemplate.update(SQL_INSERT_USER_NOTE_ID, userID, noteID);
    }
}
