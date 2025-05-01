package org.example.web_project.Service;

import org.example.web_project.Repository.AccessTokenRepository;
import org.example.web_project.Repository.UserTokenRepository;
import org.example.web_project.Security.AccessToken;
import org.example.web_project.SessionStorage.UserSessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;;

@Service
public class AccessTokenService {

    private final AccessTokenRepository accessTokenRepository;
    private final UserTokenRepository userTokenRepository;

    private final UserSessionStorage userSessionStorage;


    @Autowired
    public AccessTokenService(AccessTokenRepository accessTokenRepository, UserTokenRepository userTokenRepository, UserSessionStorage userSessionStorage) {
        this.accessTokenRepository = accessTokenRepository;
        this.userTokenRepository = userTokenRepository;
        this.userSessionStorage = userSessionStorage;
    }

    private void addAccessToken() {
        AccessToken accessToken = new AccessToken();
        String token = accessToken.generateToken();

        Long tokenID = accessTokenRepository.addAccessToken(token);

        userSessionStorage.addTokenID(tokenID);
    }

    public void userADDToken(){
        addAccessToken();
    }
}
