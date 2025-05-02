package org.example.web_project.Service;

import org.example.web_project.Entity.UsersDBEntity;
import org.example.web_project.Repository.*;
import org.example.web_project.SessionStorage.UserSessionStorage;
import org.example.web_project.UserController.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final UserTokenRepository userTokenRepository;
    private final AccessTokenRepository accessTokenRepository;

    private final UserSessionStorage userSessionStorage;

    @Autowired
    public UsersService(UsersRepository usersRepository, UserTokenRepository userTokenRepository, AccessTokenRepository accessTokenRepository, UserSessionStorage userSessionStorage) {
        this.usersRepository = usersRepository;
        this.userTokenRepository = userTokenRepository;
        this.accessTokenRepository = accessTokenRepository;
        this.userSessionStorage = userSessionStorage;
    }

    public void addNewUser(UserRequest userRequest) {
        UsersDBEntity usersDBEntity = new UsersDBEntity();
        usersDBEntity.setName(userRequest.getName());
        usersDBEntity.setEmail(userRequest.getEmail());
        usersDBEntity.setPassword(userRequest.getPassword());
        usersDBEntity.setUser_name(userRequest.getUser_name());
        usersDBEntity.setSurname(userRequest.getSurname());
        usersDBEntity.setPhone_number(userRequest.getPhone_number());
        usersDBEntity.setPassword(userRequest.getPassword());

        userSessionStorage.addUserID(usersRepository.addNewUser(usersDBEntity));

        userTokenRepository.addToken(userSessionStorage.getUserID(), userSessionStorage.getTokenID());
    }

    public void loginUser(UserRequest userRequest) {
        UsersDBEntity usersDBEntity = new UsersDBEntity();
        usersDBEntity.setName(userRequest.getName());
        usersDBEntity.setEmail(userRequest.getEmail());
        usersDBEntity.setPassword(userRequest.getPassword());
        usersDBEntity.setUser_name(userRequest.getUser_name());
        usersDBEntity.setSurname(userRequest.getSurname());
        usersDBEntity.setPhone_number(userRequest.getPhone_number());
        usersDBEntity.setPassword(userRequest.getPassword());

        usersRepository.loginUser(usersDBEntity);
    }
}
