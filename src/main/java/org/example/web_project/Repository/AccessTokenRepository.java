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

    private final JdbcTemplate jdbcTemplate;

    private final UserSessionStorage userSessionStorage;

    @Autowired
    public AccessTokenRepository(JdbcTemplate jdbcTemplate, UserSessionStorage userSessionStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userSessionStorage = userSessionStorage;
    }

    public Long addAccessToken(String token) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String QUERY = "INSERT INTO acces_token(token) VALUES (?)";

        jdbcTemplate.update(connect -> {
            PreparedStatement ps = connect.prepareStatement(QUERY, new String[]{"id"});
            ps.setString(1, token);
            return ps;
        },keyHolder);

        return keyHolder.getKey().longValue();
    }

    public String assignTokenToLoginUser() {
        Long userID = userSessionStorage.getUserID();

        String QUERY = "SELECT token_id FROM user_token WHERE user_id = ?";

        Long token_id = jdbcTemplate.queryForObject(
                QUERY,
                new Object[]{userID},
                Long.class
        );

        String QUERY2 = "SELECT token FROM acces_token WHERE id = ?";

        String token = jdbcTemplate.queryForObject(
                QUERY2,
                new Object[]{token_id},
                String.class
        );
        return token;
    }
}
