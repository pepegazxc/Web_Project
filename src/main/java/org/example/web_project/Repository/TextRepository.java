package org.example.web_project.Repository;

import org.example.web_project.Entity.TextDBEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class TextRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TextRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long addNewText(TextDBEntity textDBEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String QUERY = "INSERT INTO texts(text) VALUES (?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(QUERY, new String[]{"id"});
            ps.setString(1, textDBEntity.getText());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
}
