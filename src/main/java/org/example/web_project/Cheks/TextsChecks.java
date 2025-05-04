package org.example.web_project.Cheks;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TextsChecks {

    public void checkList(List<String> usersTexts){
        if (!usersTexts.isEmpty()) {
            usersTexts.clear();
        }
    }
}
