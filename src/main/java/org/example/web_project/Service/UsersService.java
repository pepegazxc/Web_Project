package org.example.web_project.Service;

import org.example.web_project.Entity.UsersDBEntity;
import org.example.web_project.Repository.*;
import org.example.web_project.SessionStorage.UserSessionStorage;
import org.example.web_project.DTO.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    private final UserSessionStorage userSessionStorage;

    @Autowired
    public UsersService(UsersRepository usersRepository, UserSessionStorage userSessionStorage) {
        this.usersRepository = usersRepository;
        this.userSessionStorage = userSessionStorage;
    }

    public void addNewUser(UserRequest userRequest) {
        UsersDBEntity user = new UsersDBEntity.Builder()
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .phone_number(userRequest.getPhone_number())
                .email(userRequest.getEmail())
                .user_name(userRequest.getUser_name())
                .password(userRequest.getPassword())
                .build();

        userSessionStorage.addUserID(usersRepository.addNewUser(user));
    }

    public void loginUser(UserRequest userRequest) {
        UsersDBEntity user = new UsersDBEntity.Builder()
                .password(userRequest.getPassword())
                .user_name(userRequest.getUser_name())
                .build();

        usersRepository.loginUser(user);
    }
}
