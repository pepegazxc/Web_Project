package org.example.web_project.Service;

import org.example.web_project.Entity.UsersDataBaseEntity;
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
        UsersDataBaseEntity user = new UsersDataBaseEntity.Builder()
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .phoneNumber(userRequest.getPhone_number())
                .email(userRequest.getEmail())
                .userName(userRequest.getUser_name())
                .password(userRequest.getPassword())
                .build();

        userSessionStorage.addUserID(usersRepository.addNewUser(user));
    }

    public void loginUser(UserRequest userRequest) {
        UsersDataBaseEntity user = new UsersDataBaseEntity.Builder()
                .password(userRequest.getPassword())
                .userName(userRequest.getUser_name())
                .build();

        usersRepository.loginUser(user);
    }
}
