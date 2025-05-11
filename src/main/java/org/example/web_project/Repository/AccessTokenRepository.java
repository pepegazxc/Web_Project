package org.example.web_project.Repository;

import org.example.web_project.Entity.Access_tokenDBEntity;
import org.example.web_project.Security.AccessToken;
import org.example.web_project.SessionStorage.UserSessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class AccessTokenRepository {
    private static final String SQL_INSERT_NEW_TOKEN =
            "INSERT INTO acces_token(token) VALUES (?)";

    private static final String SQL_SELECT_TOKEN_ID_BY_USER_ID
            = "SELECT token_id FROM user_token WHERE user_id = ?";

    private static final String SQL_SELECT_TOKEN_FROM_DB =
            "SELECT token FROM acces_token WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    private final UserSessionStorage userSessionStorage;

    @Autowired
    public AccessTokenRepository(JdbcTemplate jdbcTemplate, UserSessionStorage userSessionStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userSessionStorage = userSessionStorage;
    }

    public Long addAccessToken(String token) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connect -> {
            PreparedStatement ps = connect.prepareStatement(SQL_INSERT_NEW_TOKEN, new String[]{"id"});
            ps.setString(1, token);
            return ps;
        },keyHolder);

        return keyHolder.getKey().longValue();
    }

    public String assignTokenToLoginUser() {
        Long userID = userSessionStorage.getUserID();

        Long token_id = jdbcTemplate.queryForObject(
                SQL_SELECT_TOKEN_ID_BY_USER_ID,
                new Object[]{userID},
                Long.class
        );

        String token = jdbcTemplate.queryForObject(
                SQL_SELECT_TOKEN_FROM_DB,
                new Object[]{token_id},
                String.class
        );
        return token;
    }
}
