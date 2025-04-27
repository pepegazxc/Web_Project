package org.example.web_project.Service;

import org.example.web_project.Repository.AccessTokenRepository;
import org.example.web_project.Security.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenService {

    private final AccessTokenRepository accessTokenRepository;

    @Autowired
    public AccessTokenService(AccessTokenRepository accessTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
    }

    public void addAccessToken(AccessToken accessToken) {
        String token = accessToken.generateToken();
        accessTokenRepository.addAccessToken(token);
    }
}
