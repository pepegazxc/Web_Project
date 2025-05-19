package org.example.web_project.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UsersDataBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    @Column(unique = true)
    private String phone_number;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String user_name;

    private String password;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static class Builder {
        private final UsersDataBaseEntity user = new UsersDataBaseEntity();

        public Builder name(String name) {
            user.setName(name);
            return this;
        }
        public Builder surname(String surname) {
            user.setSurname(surname);
            return this;
        }
        public Builder phone_number(String phone_number) {
            user.setPhone_number(phone_number);
            return this;
        }
        public Builder email(String email) {
            user.setEmail(email);
            return this;
        }
        public Builder user_name(String user_name) {
            user.setUser_name(user_name);
            return this;
        }
        public Builder password(String password) {
            user.setPassword(password);
            return this;
        }
        public UsersDataBaseEntity build() {
            return user;
        }
    }
}
