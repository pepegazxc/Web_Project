package org.example.web_project.Repository;

import org.example.web_project.Entity.Access_tokenDBEntity;
import org.example.web_project.Security.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccessTokenRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccessTokenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addAccessToken(String token) {
        String QUERY = "INSERT INTO acces_token(token) VALUES (?)";

        jdbcTemplate.update(QUERY, token);

        //Сделать так чтобы токен был константой при создании

    }
}
