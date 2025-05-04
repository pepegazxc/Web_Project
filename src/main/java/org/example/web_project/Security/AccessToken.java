package org.example.web_project.Security;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccessToken {

    public String generateToken() {
        return accessToken();
    }

    private String accessToken (){
        return UUID.randomUUID().toString();
    }

}
