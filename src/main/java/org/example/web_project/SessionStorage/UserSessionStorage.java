package org.example.web_project.SessionStorage;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSessionStorage {

    // Передача и работа с добавлением в случае надобности и т.д
    private Long userID;
    private Long tokenID;

    // САМ ACCESS TOKEN!!!
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
