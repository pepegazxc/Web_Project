package org.example.web_project.Service;

import org.example.web_project.Entity.TextDBEntity;
import org.example.web_project.Entity.UsersDBEntity;
import org.example.web_project.Exceptions.EmptyStorage;
import org.example.web_project.Repository.TextRepository;
import org.example.web_project.Repository.UserTextRepository;
import org.example.web_project.Repository.UserTokenRepository;
import org.example.web_project.SessionStorage.UserSessionStorage;
import org.example.web_project.UserController.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

        userTokenRepository.checkToken();
        TextDBEntity textDBEntity = new TextDBEntity();
        UsersDBEntity usersDBEntity = new UsersDBEntity();
        textDBEntity.setText(userRequest.getText());
        textDBEntity.setId(userRequest.getId());
        usersDBEntity.setId(userRequest.getId());
        Long text_id = textRepository.addNewText(textDBEntity);
        Long user_id = userSessionStorage.getUserID();
        userTextRepository.addNewUserText(user_id, text_id);
    }

    public Map<Long, String> showAllTexts() {
        userTokenRepository.checkToken();
        return textRepository.showAllTexts();
    }

    public void deleteAllTexts() {
        userTokenRepository.checkToken();
        textRepository.deleteAllTexts();
    }

    public void deleteChosenText(UserRequest userRequest) {
        userTokenRepository.checkToken();
        TextDBEntity textDBEntity = new TextDBEntity();
        textDBEntity.setId(userRequest.getId());
        textRepository.deleteChosenText(textDBEntity);
    }

    public void updateChosenText(UserRequest userRequest) {
        userTokenRepository.checkToken();
        TextDBEntity textDBEntity = new TextDBEntity();
        textDBEntity.setId(userRequest.getId());
        textDBEntity.setText(userRequest.getText());
        textRepository.updateChosenText(textDBEntity);
    }
}
