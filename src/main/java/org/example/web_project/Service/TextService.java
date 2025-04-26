package org.example.web_project.Service;

import org.example.web_project.Entity.TextDBEntity;
import org.example.web_project.Entity.User_textDBEntity;
import org.example.web_project.Entity.UsersDBEntity;
import org.example.web_project.Repository.TextRepository;
import org.example.web_project.Repository.UserTextRepository;
import org.example.web_project.UserController.UserRequest;
import org.springframework.stereotype.Service;

@Service
public class    TextService {

    private final TextRepository textRepository;
    private final UserTextRepository userTextRepository;


    public TextService(TextRepository textRepository, UserTextRepository userTextRepository) {
        this.textRepository = textRepository;
        this.userTextRepository = userTextRepository;
    }

    public void addNewText(UserRequest userRequest) {
        TextDBEntity textDBEntity = new TextDBEntity();
        UsersDBEntity usersDBEntity = new UsersDBEntity();
        textDBEntity.setText(userRequest.getText());
        textDBEntity.setId(userRequest.getId());
        usersDBEntity.setId(userRequest.getId());

        Long textId = textRepository.addNewText(textDBEntity);

        userTextRepository.addNewUserText(usersDBEntity, textId);
    }
}
