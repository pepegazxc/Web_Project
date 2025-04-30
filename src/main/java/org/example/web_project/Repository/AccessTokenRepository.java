package org.example.web_project.Repository;

import org.example.web_project.Entity.Access_tokenDBEntity;
import org.example.web_project.Security.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class AccessTokenRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccessTokenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String addAccessToken(String token) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String QUERY = "INSERT INTO acces_token(token) VALUES (?)";

        jdbcTemplate.update(connect -> {
            PreparedStatement ps = connect.prepareStatement(QUERY);
            ps.setString(1, token);
            return ps;
        },keyHolder);

        return keyHolder.getKey().toString();
    }
}
