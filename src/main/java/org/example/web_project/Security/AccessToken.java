package org.example.web_project.Security;

import java.util.UUID;

public class AccessToken {

    public String generateToken() {
        return accessToken();
    }

    private String accessToken (){
        return UUID.randomUUID().toString();
    }

}
