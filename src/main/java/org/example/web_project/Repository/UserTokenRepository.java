package org.example.web_project.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserTokenRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserTokenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addToken(Long userID, Long tokenID) {
        String QUERY  = "INSERT INTO user_token(user_id, token_id) VALUES (?, ?)";

        jdbcTemplate.update(QUERY, userID, tokenID);
    }
}
