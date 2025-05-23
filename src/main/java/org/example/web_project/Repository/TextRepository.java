package org.example.web_project.Repository;

import org.example.web_project.Cheks.TextsChecks;
import org.example.web_project.Entity.TextDataBaseEntity;
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

    private static final String SQL_DELETE_USER_ID_AND_TEXT_ID_BY_USER_ID =
            "DELETE FROM user_text WHERE user_id = ?";

    private static final String SQL_DELETE_USER_ID_AND_TEXT_ID_BY_TEXT_ID =
            "DELETE FROM user_text WHERE text_id = ?";

    private static final String SQL_DELETE_TEXT_BY_ID = "DELETE FROM texts WHERE id = ?";


    private final JdbcTemplate jdbcTemplate;
    private final TextsChecks textsChecks;

    @Autowired
    public TextRepository(JdbcTemplate jdbcTemplate, TextsChecks textsChecks) {
        this.jdbcTemplate = jdbcTemplate;
        this.textsChecks = textsChecks;
    }

    public Long addNewText(TextDataBaseEntity textDBEntity) {
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
        List<Long> textIds = addIdsToList(userID);

        jdbcTemplate.update(SQL_DELETE_USER_ID_AND_TEXT_ID_BY_USER_ID, userID);

        for (Long id : textIds) {
            jdbcTemplate.update("DELETE FROM texts WHERE id = ?", id);
        }

        return "All texts have been deleted!";
    }

    public String deleteChosenText(TextDataBaseEntity textDBEntity, Long userID) {
        Map<Long, String> usersTexts = addTextToMap(userID);

        textsChecks.checkingForTextsInMap(usersTexts);
        textsChecks.chooseTextIDCheck(textDBEntity);

        if(!isTextBelongsToUser(textDBEntity.getId(), userID)) {
            throw new NoAccessToDelete("You are don't allow to delete this text!");
        }

        usersTexts.remove(textDBEntity.getId());

        jdbcTemplate.update(SQL_DELETE_USER_ID_AND_TEXT_ID_BY_TEXT_ID, textDBEntity.getId());
        jdbcTemplate.update(SQL_DELETE_TEXT_BY_ID, textDBEntity.getId());

        return "Chosen texts have been deleted!";
    }

    public String updateChosenText(TextDataBaseEntity textDBEntity, Long userID) {
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

    private Map<Long, String> addTextToMap(Long userID) {
        List<Long> textIds = addIdsToList(userID);

        String inSql = String.join(",", Collections.nCopies(textIds.size(), "?"));
        String selectFromTextById = "SELECT * FROM texts WHERE id IN (" + inSql + ")";

        return jdbcTemplate.query(selectFromTextById,
                textIds.toArray(),
                rs -> {
            Map<Long, String> result = new HashMap<>();
            while(rs.next()){
                result.put(rs.getLong("id"), rs.getString("text"));
            }
            return result;
        });
    }

    private List<Long> addIdsToList(Long userID){
        List<Long> textIds = jdbcTemplate.queryForList(SQL_SELECT_TEXT_ID,
                Long.class,
                userID
        );

        textsChecks.checkingForIDInList(textIds);

        return textIds;
    }

    private boolean isTextBelongsToUser(Long textId, Long userId) {
        String checkOwnership = """
        SELECT COUNT(*) FROM user_text WHERE text_id = ? AND user_id = ?
    """;
        Integer count = jdbcTemplate.queryForObject(checkOwnership, Integer.class, textId, userId);
        return count != null && count > 0;
    }
}
