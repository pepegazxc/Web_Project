package org.example.web_project.Repository;

import org.example.web_project.Cheks.TextsChecks;
import org.example.web_project.Entity.TextDBEntity;
import org.example.web_project.Exceptions.NoAccessToEditing;
import org.example.web_project.Exceptions.NoAccessToDelete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

@Repository
public class TextRepository {
    private static final String SQL_SELECT_TEXT_ID =
            "SELECT text_id FROM user_text WHERE user_id = ?";

    private static final String SQL_UPDATE_SPECIFIC_TEXT_BY_ID =
            "UPDATE texts SET text = ? WHERE id = ?";

    private static final String SQL_INSERT_NEW_TEXT =
            "INSERT INTO texts(text) VALUES (?)";

    private static final String SQL_SELECT_TEXT_ID_FROM_USER_TEXT =
            "SELECT text_id FROM user_text WHERE user_id = ?";


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

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_NEW_TEXT, new String[]{"id"});
                ps.setString(1, textDBEntity.getText());
                return ps;
            }, keyHolder);

            return keyHolder.getKey().longValue();
    }

    public Map<Long, String> showAllTexts(Long userID) {
        Map<Long, String> usersTexts = addTextToMap(userID);

        textsChecks.checkingForTextsInMap(usersTexts);

        return usersTexts;
    }

    public String deleteAllTexts(Long userID) {
        List<Long> text_ids = jdbcTemplate.queryForList(
                SQL_SELECT_TEXT_ID_FROM_USER_TEXT,
                Long.class,
                userID);

        textsChecks.checkingForIDInList(text_ids);

        String SQL_DELETE_IDS = "DELETE FROM user_text WHERE user_id = ?";
        jdbcTemplate.update(SQL_DELETE_IDS, userID);

        for (Long id : text_ids) {
            jdbcTemplate.update("DELETE FROM texts WHERE id = ?", id);
        }

        return "All texts have been deleted!";
    }

    public String deleteChosenText(TextDBEntity textDBEntity, Long userID) {
        Map<Long, String> usersTexts = addTextToMap(userID);

        textsChecks.checkingForTextsInMap(usersTexts);
        textsChecks.chooseTextIDCheck(textDBEntity);

        if(!isTextBelongsToUser(textDBEntity.getId(), userID)) {
            throw new NoAccessToDelete("You are don't allow to delete this text!");
        }

        usersTexts.remove(textDBEntity.getId());

        String SQL_DELETE_SPECIFIC_IDS_BY_ID = "DELETE FROM user_text WHERE text_id = ?";
        String SQL_DELETE_SPECIFIC_TEXT_BY_ID = "DELETE FROM texts WHERE id = ?";

        jdbcTemplate.update(SQL_DELETE_SPECIFIC_IDS_BY_ID, textDBEntity.getId());
        jdbcTemplate.update(SQL_DELETE_SPECIFIC_TEXT_BY_ID, textDBEntity.getId());

        return "Chosen texts have been deleted!";
    }

    public String updateChosenText(TextDBEntity textDBEntity, Long userID) {
        Map<Long, String> usersTexts = addTextToMap(userID);

        textsChecks.checkingForTextsInMap(usersTexts);
        textsChecks.textFieldCheck(textDBEntity);
        textsChecks.chooseTextIDCheck(textDBEntity);

        if(!isTextBelongsToUser(textDBEntity.getId(), userID)) {
            throw new NoAccessToEditing("You are don't allow to update this text!");
        }

        jdbcTemplate.update(SQL_UPDATE_SPECIFIC_TEXT_BY_ID, textDBEntity.getText(), textDBEntity.getId());

        return "Chosen texts have been updated!";
    }


    private List<String> addTextToList(Long userID) {
        List<Long> text_ids = jdbcTemplate.queryForList(SQL_SELECT_TEXT_ID,
                Long.class,
                userID
        );

        textsChecks.checkingForIDInList(text_ids);

        String IN_SQL = String.join(",", Collections.nCopies(text_ids.size(), "?"));
        String SQL_SELECT_USER_TEXTS = "SELECT * FROM texts WHERE id IN (" + IN_SQL + ")";

        return jdbcTemplate.queryForList(SQL_SELECT_USER_TEXTS, String.class, text_ids.toArray());
    }

    private Map<Long, String> addTextToMap(Long userID) {
        List<Long> text_ids = jdbcTemplate.queryForList(SQL_SELECT_TEXT_ID,
                Long.class,
                userID
        );

        textsChecks.checkingForIDInList(text_ids);

        String IN_SQL = String.join(",", Collections.nCopies(text_ids.size(), "?"));
        String SQL_SELECT_USER_TEXTS = "SELECT * FROM texts WHERE id IN (" + IN_SQL + ")";

        return jdbcTemplate.query(SQL_SELECT_USER_TEXTS,
                text_ids.toArray(),
                rs -> {
            Map<Long, String> result = new HashMap<>();
            while(rs.next()){
                result.put(rs.getLong("id"), rs.getString("text"));
            }
            return result;
        });
    }

    private boolean isTextBelongsToUser(Long textId, Long userId) {
        String SQL_CHECK_OWNERSHIP = """
        SELECT COUNT(*) FROM user_text WHERE text_id = ? AND user_id = ?
    """;
        Integer count = jdbcTemplate.queryForObject(SQL_CHECK_OWNERSHIP, Integer.class, textId, userId);
        return count != null && count > 0;
    }
}
