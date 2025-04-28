package org.example.web_project.Repository;

import org.example.web_project.Entity.UsersDBEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

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
    }

    public void  loginUser(UsersDBEntity usersDBEntity) {
        String QUERY = "SELECT * FROM users WHERE name = ? AND surname = ? AND phone_number = ? AND email=? AND user_name=? AND password=? ";

        jdbcTemplate.update(QUERY, usersDBEntity.getName(), usersDBEntity.getSurname(), usersDBEntity.getPhone_number(), usersDBEntity.getEmail(), usersDBEntity.getUser_name() ,usersDBEntity.getPassword());
    }
}
