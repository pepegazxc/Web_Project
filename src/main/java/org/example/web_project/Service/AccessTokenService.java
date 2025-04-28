package org.example.web_project.Service;

import org.example.web_project.Repository.AccessTokenRepository;
import org.example.web_project.Security.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;;

@Service
public class AccessTokenService {

    private final List<String> user_token = new ArrayList<>();

    private final AccessTokenRepository accessTokenRepository;

    @Autowired
    public AccessTokenService(AccessTokenRepository accessTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
    }

    public void addAccessToken() {
        AccessToken accessToken = new AccessToken();
        String token = accessToken.generateToken();
        user_token.add(token);
        accessTokenRepository.addAccessToken(token);
    }
}
