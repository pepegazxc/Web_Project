package org.example.web_project.Service;

import org.example.web_project.Entity.UsersDBEntity;
import org.example.web_project.Repository.AccessTokenRepository;
import org.example.web_project.Repository.UserTokenRepository;
import org.example.web_project.Repository.UsersRepository;
import org.example.web_project.Security.AccessToken;
import org.example.web_project.SessionStorage.UserSessionStorage;
import org.example.web_project.UserController.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;;

@Service
public class AccessTokenService {

    private final AccessTokenRepository accessTokenRepository;
    private final UsersRepository usersRepository;
    private final UserTokenRepository userTokenRepository;

    private final UserSessionStorage userSessionStorage;


    @Autowired
    public AccessTokenService(AccessTokenRepository accessTokenRepository, UsersRepository usersRepository, UserTokenRepository userTokenRepository, UserSessionStorage userSessionStorage) {
        this.accessTokenRepository = accessTokenRepository;
        this.usersRepository = usersRepository;
        this.userTokenRepository = userTokenRepository;
        this.userSessionStorage = userSessionStorage;
    }

    public void assignTokenToLoginUser(UserRequest userRequest) {
        UsersDBEntity usersDBEntity = new UsersDBEntity.Builder()
                .user_name(userRequest.getUser_name())
                .build();
        userSessionStorage.addUserID(usersRepository.getUserID(usersDBEntity));
        userSessionStorage.addToken(accessTokenRepository.assignTokenToLoginUser());
    }

    public void assignTokenToNewUser() {
        AccessToken accessToken = new AccessToken();
        userSessionStorage.addToken(
                accessToken.generateToken()
        );

        Long tokenID = accessTokenRepository.addAccessToken(
                userSessionStorage.getToken()
        );

        userSessionStorage.addTokenID(tokenID);
        userTokenRepository.addToken(userSessionStorage.getUserID(), userSessionStorage.getTokenID());
    }
}
