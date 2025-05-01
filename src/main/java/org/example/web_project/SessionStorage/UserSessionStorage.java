package org.example.web_project.SessionStorage;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSessionStorage {

    private Long userID;
    private Long tokenID;

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


}
