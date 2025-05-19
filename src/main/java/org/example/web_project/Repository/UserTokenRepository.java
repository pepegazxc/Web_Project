package org.example.web_project.Repository;

import org.example.web_project.Exceptions.TokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;


@Repository
public class UserTokenRepository {
    private static final String SQL_INSERT_USER_TOKEN_ID  =
            "INSERT INTO user_token(user_id, token_id) VALUES (?, ?)";

    private static final String SQL_SELECT_FOR_TOKEN_ID =
            "SELECT id FROM acces_token WHERE token = ?";

    private static final String SQL_SELECT_FOR_USER_ID =
            "SELECT user_id FROM user_token WHERE token_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserTokenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addToken(Long userID, Long tokenID) {
        jdbcTemplate.update(SQL_INSERT_USER_TOKEN_ID, userID, tokenID);
    }

    public void checkToken(String token, Long userID) {

            Long tokenId = jdbcTemplate.queryForObject(
                    SQL_SELECT_FOR_TOKEN_ID,
                    Long.class,
                    token
            );

            Long userId = jdbcTemplate.queryForObject(
                    SQL_SELECT_FOR_USER_ID,
                    Long.class,
                    tokenId
            );

        if (!Objects.equals(userId, userID)) {
                throw new TokenException("Something went wrong when checking token");
            }
    }
}
