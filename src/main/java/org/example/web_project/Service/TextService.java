package org.example.web_project.Service;

import org.example.web_project.Entity.TextDBEntity;
import org.example.web_project.Repository.TextRepository;
import org.example.web_project.Repository.UserTextRepository;
import org.example.web_project.Repository.UserTokenRepository;
import org.example.web_project.SessionStorage.UserSessionStorage;
import org.example.web_project.UserController.UserRequest;
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
        Long user_id = userSessionStorage.getUserID();

        userTokenRepository.checkToken(token, user_id);
        TextDBEntity textDBEntity = new TextDBEntity.Builder()
                .id(userRequest.getId())
                .text(userRequest.getText())
                .build();

        Long text_id = textRepository.addNewText(textDBEntity);

        userTextRepository.addNewUserText(user_id, text_id);
    }

    public Map<Long, String> showAllTexts() {
        String token = userSessionStorage.getToken();
        Long user_id = userSessionStorage.getUserID();

        userTokenRepository.checkToken(token, user_id);
        return textRepository.showAllTexts(user_id);
    }

    public void deleteAllTexts() {
        String token = userSessionStorage.getToken();
        Long user_id = userSessionStorage.getUserID();

        userTokenRepository.checkToken(token, user_id);
        textRepository.deleteAllTexts(user_id);
    }

    public void deleteChosenText(UserRequest userRequest) {
        String token = userSessionStorage.getToken();
        Long user_id = userSessionStorage.getUserID();

        userTokenRepository.checkToken(token, user_id);
        TextDBEntity textDBEntity = new TextDBEntity.Builder()
                .id(userRequest.getId())
                .build();

        textRepository.deleteChosenText(textDBEntity, user_id);
    }

    public void updateChosenText(UserRequest userRequest) {
        String token = userSessionStorage.getToken();
        Long user_id = userSessionStorage.getUserID();

        userTokenRepository.checkToken(token, user_id);
        TextDBEntity textDBEntity = new TextDBEntity.Builder()
                .id(userRequest.getId())
                .text(userRequest.getText())
                .build();

        textRepository.updateChosenText(textDBEntity, user_id);
    }
}
