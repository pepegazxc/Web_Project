package org.example.web_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public class RepositoryForDB {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RepositoryForDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addTextToDB(@RequestBody UserRequest userRequest) {
        String sql = "INSERT INTO tasks VALUES (?)";

        jdbcTemplate.update(sql, userRequest.getText());
    }
}
