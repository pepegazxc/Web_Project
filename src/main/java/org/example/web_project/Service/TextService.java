package org.example.web_project.Service;

import org.example.web_project.Entity.TextDBEntity;
import org.example.web_project.Entity.User_textDBEntity;
import org.example.web_project.Entity.UsersDBEntity;
import org.example.web_project.Repository.TextRepository;
import org.example.web_project.Repository.UserTextRepository;
import org.example.web_project.Repository.UsersRepository;
import org.example.web_project.UserController.UserRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextService {

    private final TextRepository textRepository;
    private final UserTextRepository userTextRepository;
    private final UsersRepository usersRepository;


    public TextService(TextRepository textRepository, UserTextRepository userTextRepository, UsersRepository usersRepository) {
        this.textRepository = textRepository;
        this.userTextRepository = userTextRepository;
        this.usersRepository = usersRepository;
    }

    public void addNewText(UserRequest userRequest) {
        TextDBEntity textDBEntity = new TextDBEntity();
        UsersDBEntity usersDBEntity = new UsersDBEntity();
        textDBEntity.setText(userRequest.getText());
        textDBEntity.setId(userRequest.getId());
        usersDBEntity.setId(userRequest.getId());

        Long text_id = textRepository.addNewText(textDBEntity);
        Long user_id = usersRepository.addNewUser(usersDBEntity);

        userTextRepository.addNewUserText(user_id, text_id);
    }
}
