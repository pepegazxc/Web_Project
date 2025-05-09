package org.example.web_project.Repository;

import org.example.web_project.Exceptions.TokenException;
import org.example.web_project.SessionStorage.UserSessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;


@Repository
public class UserTokenRepository {

    private final JdbcTemplate jdbcTemplate;

    private final UserSessionStorage userSessionStorage;

    @Autowired
    public UserTokenRepository(JdbcTemplate jdbcTemplate, UserSessionStorage userSessionStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userSessionStorage = userSessionStorage;
    }

    public void addToken(Long userID, Long tokenID) {
        String QUERY  = "INSERT INTO user_token(user_id, token_id) VALUES (?, ?)";

        jdbcTemplate.update(QUERY, userID, tokenID);
    }

    public void checkToken() {

            String QUERY = "SELECT id FROM acces_token WHERE token = ?";
            Long token_id = jdbcTemplate.queryForObject(
                    QUERY,
                    Long.class,
                    userSessionStorage.getToken()
            );

            String QUERY2 = "SELECT user_id FROM user_token WHERE token_id = ?";
            Long user_id = jdbcTemplate.queryForObject(
                    QUERY2,
                    Long.class,
                    token_id
            );

        if (!Objects.equals(user_id, userSessionStorage.getUserID())) {
                throw new TokenException("Something went wrong when checking token");
            }
    }
}
