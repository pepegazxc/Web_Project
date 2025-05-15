package org.example.web_project.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserTextRepository {
    private static final String SQL_INSERT_USER_TEXT_ID =
            "INSERT INTO user_text(user_id, text_id) VALUES (?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserTextRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addNewUserText(Long userId, Long textId) {
        jdbcTemplate.update(SQL_INSERT_USER_TEXT_ID, userId, textId);
    }
}
