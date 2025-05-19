package org.example.web_project.Service;

import org.example.web_project.Entity.TextDataBaseEntity;
import org.example.web_project.Repository.TextRepository;
import org.example.web_project.Repository.UserTextRepository;
import org.example.web_project.Repository.UserTokenRepository;
import org.example.web_project.SessionStorage.UserSessionStorage;
import org.example.web_project.DTO.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TextService {

    private final TextRepository textRepository;
    private final UserTextRepository userTextRepository;
    private final UserTokenRepository userTokenRepository;

    private final UserSessionStorage userSessionStorage;

    @Autowired
    public TextService(TextRepository textRepository, UserTextRepository userTextRepository, UserTokenRepository userTokenRepository, UserSessionStorage userSessionStorage) {
        this.textRepository = textRepository;
        this.userTextRepository = userTextRepository;
        this.userTokenRepository = userTokenRepository;
        this.userSessionStorage = userSessionStorage;
    }

    public void addNewText(UserRequest userRequest) {
        String token = userSessionStorage.getToken();
        Long userId = userSessionStorage.getUserID();

        userTokenRepository.checkToken(token, userId);
        TextDataBaseEntity textDBEntity = new TextDataBaseEntity.Builder()
                .text(userRequest.getText())
                .build();

        Long textId = textRepository.addNewText(textDBEntity);

        userTextRepository.addNewUserText(userId, textId);
    }

    public Map<Long, String> showAllTexts() {
        String token = userSessionStorage.getToken();
        Long userId = userSessionStorage.getUserID();

        userTokenRepository.checkToken(token, userId);
        return textRepository.showAllTexts(userId);
    }

    public void deleteAllTexts() {
        String token = userSessionStorage.getToken();
        Long userId = userSessionStorage.getUserID();

        userTokenRepository.checkToken(token, userId);
        textRepository.deleteAllTexts(userId);
    }

    public void deleteChosenText(UserRequest userRequest) {
        String token = userSessionStorage.getToken();
        Long userId = userSessionStorage.getUserID();

        userTokenRepository.checkToken(token, userId);
        TextDataBaseEntity textDBEntity = new TextDataBaseEntity.Builder()
                .id(userRequest.getId())
                .build();

        textRepository.deleteChosenText(textDBEntity, userId);
    }

    public void updateChosenText(UserRequest userRequest) {
        String token = userSessionStorage.getToken();
        Long userId = userSessionStorage.getUserID();

        userTokenRepository.checkToken(token, userId);
        TextDataBaseEntity textDBEntity = new TextDataBaseEntity.Builder()
                .id(userRequest.getId())
                .text(userRequest.getText())
                .build();

        textRepository.updateChosenText(textDBEntity, userId);
    }
}
