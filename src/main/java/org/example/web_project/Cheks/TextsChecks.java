package org.example.web_project.Cheks;

import org.example.web_project.Entity.TextDBEntity;
import org.example.web_project.Entity.UsersDBEntity;
import org.example.web_project.Exceptions.EmptyID;
import org.example.web_project.Exceptions.EmptyRequest;
import org.example.web_project.Exceptions.EmptyStorage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TextsChecks {

    public void textFieldCheck (TextDBEntity textDBEntity) {
        if (textDBEntity.getText() == null || textDBEntity.getText().isEmpty()) {
            throw new EmptyRequest("Please, fill in all the fields");
        }
    }

    public void chooseTextIDCheck (TextDBEntity textDBEntity) {
        if (textDBEntity.getId() == null || textDBEntity.getId() == 0) {
            throw new EmptyRequest("Please, fill in all the fields");
        }
    }

    public void checkingForTextsInList(List<String> usersTexts) {
        if (usersTexts == null || usersTexts.isEmpty()) {
            throw new EmptyStorage("Right now you don't have any texts");
        }
    }

    public void checkingForTextsInMap(Map<Long, String> usersTexts) {
        if (usersTexts == null || usersTexts.isEmpty()) {
            throw new EmptyStorage("Right now you don't have any texts");
        }
    }

    public void checkingForIDInList(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new EmptyID("IDs list is empty");
        }
    }
}
