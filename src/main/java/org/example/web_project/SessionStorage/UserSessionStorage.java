package org.example.web_project.SessionStorage;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSessionStorage {

    private final List<Long> userID = new ArrayList<>();
    private final List<String> token = new ArrayList<>();

    public List<Long> getUserID() {
        return userID;
    }
    public List<String> getToken() {
        return token;
    }

    public void addToken(String token) {
        this.token.add(token);
    }

    public void addUserID(long userID) {
        this.userID.add(userID);
    }


}
