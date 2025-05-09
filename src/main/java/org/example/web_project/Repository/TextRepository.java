package org.example.web_project.Repository;

import org.example.web_project.Cheks.TextsChecks;
import org.example.web_project.Entity.TextDBEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<Long, String> showAllTexts() {
        Map<Long, String> usersTexts = addTextToMap();

        textsChecks.checkingForTextsInMap(usersTexts);

        return usersTexts;
    }

    public String deleteAllTexts() {
        List<String> usersTexts = addTextToList();

        textsChecks.checkingForTextsInList(usersTexts);

            usersTexts.clear();

            String QUERY = "DELETE FROM user_text";
            String QUERY2 = "DELETE FROM texts";

            jdbcTemplate.update(QUERY);
            jdbcTemplate.update(QUERY2);

            return "All texts have been deleted!";
    }

    public String deleteChosenText(TextDBEntity textDBEntity) {
        Map<Long, String> usersTexts = addTextToMap();

        textsChecks.checkingForTextsInMap(usersTexts);
        textsChecks.chooseTextIDCheck(textDBEntity);

        usersTexts.remove(textDBEntity.getId());

        String QUERY = "DELETE FROM user_text WHERE text_id = ?";
        String QUERY2 = "DELETE FROM texts WHERE id = ?";

        jdbcTemplate.update(QUERY, textDBEntity.getId());
        jdbcTemplate.update(QUERY2, textDBEntity.getId());

        return "Chosen texts have been deleted!";
    }

    public String updateChosenText(TextDBEntity textDBEntity) {
        Map<Long, String> usersTexts = addTextToMap();

        textsChecks.checkingForTextsInMap(usersTexts);
        textsChecks.textFieldCheck(textDBEntity);
        textsChecks.chooseTextIDCheck(textDBEntity);

        String QUERY = "UPDATE texts SET text = ? WHERE id = ?";

        jdbcTemplate.update(QUERY, textDBEntity.getText(), textDBEntity.getId());

        usersTexts.put(textDBEntity.getId(), textDBEntity.getText());

        return "Chosen texts have been updated!";
    }


    private List<String> addTextToList() {
        String QUERY = "SELECT text FROM texts ORDER BY id";
        return jdbcTemplate.queryForList(
                QUERY,
                String.class
        );
    }

    private Map<Long, String> addTextToMap() {
        String QUERY = "SELECT id, text FROM texts ORDER BY id";
        return jdbcTemplate.query(QUERY, rs -> {
            Map<Long, String> result = new HashMap<>();
            while(rs.next()){
                result.put(rs.getLong("id"), rs.getString("text"));
            }
            return result;
        });
    }
}
