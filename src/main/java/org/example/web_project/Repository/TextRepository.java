package org.example.web_project.Repository;

import org.example.web_project.Cheks.TextsChecks;
import org.example.web_project.Entity.TextDBEntity;
import org.example.web_project.Exceptions.EmptyStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TextRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TextsChecks textsChecks;

    @Autowired
    public TextRepository(JdbcTemplate jdbcTemplate, TextsChecks textsChecks) {
        this.jdbcTemplate = jdbcTemplate;
        this.textsChecks = textsChecks;
    }

    public Long addNewText(TextDBEntity textDBEntity) {
        textsChecks.textFieldCheck(textDBEntity);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            String QUERY = "INSERT INTO texts(text) VALUES (?)";

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(QUERY, new String[]{"id"});
                ps.setString(1, textDBEntity.getText());
                return ps;
            }, keyHolder);

            return keyHolder.getKey().longValue();
    }

    public List<String> showAllTexts() {
        List<String> usersTexts = addTextToList();

        textsChecks.checkingForTexts(usersTexts);

        return usersTexts;
    }

    public String deleteAllTexts() {
        List<String> usersTexts = addTextToList();

        textsChecks.checkingForTexts(usersTexts);

            usersTexts.clear();

            String QUERY = "DELETE FROM user_text";
            String QUERY2 = "DELETE FROM texts";

            jdbcTemplate.update(QUERY);
            jdbcTemplate.update(QUERY2);

            return "All texts have been deleted!";
    }

    private List<String> addTextToList() {
        String QUERY = "SELECT text FROM texts";
        return jdbcTemplate.queryForList(
                QUERY,
                String.class
        );
    }
}
