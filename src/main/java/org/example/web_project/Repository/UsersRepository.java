package org.example.web_project.Repository;

import org.example.web_project.Cheks.UserChecks;
import org.example.web_project.Entity.UsersDBEntity;
import org.example.web_project.Exceptions.UserNotFound;
import org.example.web_project.Exceptions.UserWithThatDataAlreadyExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class UsersRepository {
    private static final String SQL_INSERT_NEW_USER_DATA =
            "INSERT INTO users(name, surname, phone_number, email, user_name, password) VALUES (?, ?, ?, ?, ?, ?) ";

    private static final String SQL_SELECT_USERNAME_FOR_LOGIN_USER =
            "SELECT user_name FROM users WHERE user_name = ? AND password = ? ";

    private static final String SQL_SELECT_USER_ID =
            "SELECT id FROM users WHERE user_name = ?";

    private final JdbcTemplate jdbcTemplate;
    private final UserChecks userChecks;

    @Autowired
    public UsersRepository(JdbcTemplate jdbcTemplate, UserChecks userChecks) {
        this.jdbcTemplate = jdbcTemplate;
        this.userChecks = userChecks;
    }

    public Long addNewUser(UsersDBEntity usersDBEntity) {
        userChecks.registrationFieldCheck(usersDBEntity);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {

                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_NEW_USER_DATA, new String[]{"id"});
                ps.setString(1, usersDBEntity.getName());
                ps.setString(2, usersDBEntity.getSurname());
                ps.setString(3, usersDBEntity.getPhone_number());
                ps.setString(4, usersDBEntity.getEmail());
                ps.setString(5, usersDBEntity.getUser_name());
                ps.setString(6, usersDBEntity.getPassword());
                return ps;
                }, keyHolder);
                return keyHolder.getKey().longValue();

        } catch (DuplicateKeyException e) {
            throw new UserWithThatDataAlreadyExist("User with that data already exist. Please, enter another username, email or phone number.");
        }
    }

    public String loginUser(UsersDBEntity usersDBEntity) {
        userChecks.loginFieldCheck(usersDBEntity);

            try {
                String userName = jdbcTemplate.queryForObject(
                        SQL_SELECT_USERNAME_FOR_LOGIN_USER,
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

    public Long getUserID(UsersDBEntity usersDBEntity) {
        Long userID = jdbcTemplate.queryForObject(
                SQL_SELECT_USER_ID,
                new Object[]{
                        usersDBEntity.getUser_name()
                },
                Long.class
        );
        return userID;
    }
}

