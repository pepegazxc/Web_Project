package org.example.web_project.Repository;

import org.example.web_project.Entity.UsersDBEntity;
import org.example.web_project.Exceptions.UserNotFound;
import org.example.web_project.Exceptions.UserWithThatDataAlreadyExist;
import org.example.web_project.SessionStorage.UserSessionStorage;
import org.example.web_project.UserController.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class UsersRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long addNewUser(UsersDBEntity usersDBEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String QUERY = "INSERT INTO users(name, surname, phone_number, email, user_name, password) VALUES (?, ?, ?, ?, ?, ?) ";

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(QUERY, new String[]{"id"});
                ps.setString(1, usersDBEntity.getName());
                ps.setString(2, usersDBEntity.getSurname());
                ps.setString(3, usersDBEntity.getPhone_number());
                ps.setString(4, usersDBEntity.getEmail());
                ps.setString(5, usersDBEntity.getUser_name());
                ps.setString(6, usersDBEntity.getPassword());
                return ps;
            }, keyHolder);
            return keyHolder.getKey().longValue();
        }catch (DuplicateKeyException e){
            throw new UserWithThatDataAlreadyExist("User with that data already exist. Please, enter another username, email or phone number.");
        }
    }

    public String loginUser(UsersDBEntity usersDBEntity) {
        String QUERY = "SELECT user_name FROM users WHERE user_name = ? AND password = ? ";

        try {
            String userName = jdbcTemplate.queryForObject(
                    QUERY,
                    new Object[]{
                            usersDBEntity.getUser_name(),
                            usersDBEntity.getPassword()
                    },
                    String.class
            );
            return userName;
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFound("User not found. Please, check username and password.");
        }
    }

    public Long returnUserID(UsersDBEntity usersDBEntity) {
        return getIDUser(usersDBEntity);
    }

    private Long getIDUser(UsersDBEntity usersDBEntity) {
        String QUERY = "SELECT id FROM users WHERE user_name = ?";

        Long userID = jdbcTemplate.queryForObject(
                QUERY,
                new Object[]{
                        usersDBEntity.getUser_name()
                },
                Long.class
        );
        return userID;
    }
}
