package org.example.web_project.Repository;

import org.example.web_project.Entity.TextDBEntity;
import org.example.web_project.Entity.UsersDBEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserTextRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserTextRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addNewUserText(Long userId, Long textId) {
        String QUERY = "INSERT INTO user_text(user_id, text_id) VALUES (?, ?)";

        jdbcTemplate.update(QUERY, userId, textId);
    }
}
