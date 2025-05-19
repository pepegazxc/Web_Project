package org.example.web_project.SessionStorage;

import org.springframework.stereotype.Component;

@Component
public class UserSessionStorage {

    private Long userID;
    private Long tokenID;

    private String token;

    public Long getUserID() {
        return userID;
    }
    public Long getTokenID() {
        return tokenID;
    }

    public void addTokenID(long token) {
        this.tokenID = token;
    }

    public void addUserID(long userID) {
        this.userID = userID;
    }

    public void addToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }


}
