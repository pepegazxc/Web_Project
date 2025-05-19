package org.example.web_project.Cheks;

import org.example.web_project.Entity.UsersDataBaseEntity;
import org.example.web_project.Exceptions.EmptyRequest;
import org.springframework.stereotype.Component;

@Component
public class UserChecks {

    public void registrationFieldCheck(UsersDataBaseEntity usersDBEntity) {
        if (usersDBEntity.getName() == null || usersDBEntity.getName().isEmpty() ||
        usersDBEntity.getSurname() == null || usersDBEntity.getSurname().isEmpty() ||
        usersDBEntity.getEmail() == null || usersDBEntity.getEmail().isEmpty() ||
        usersDBEntity.getUserName() == null || usersDBEntity.getUserName().isEmpty() ||
        usersDBEntity.getPassword() == null || usersDBEntity.getPassword().isEmpty() ||
        usersDBEntity.getPhoneNumber() == null || usersDBEntity.getPhoneNumber().isEmpty()) {
            throw new EmptyRequest("Please, fill all the required fields.");
        }
    }

    public void loginFieldCheck(UsersDataBaseEntity usersDBEntity) {
        if (usersDBEntity.getUserName() == null || usersDBEntity.getUserName().isEmpty() ||
        usersDBEntity.getPassword() == null || usersDBEntity.getPassword().isEmpty()) {
            throw new EmptyRequest("Please, fill all the required fields.");
        }
    }

}
