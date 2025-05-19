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

    @Column(unique = true ,name = "phone_number")
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @Column(unique = true, name = "user_name")
    private String userName;

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
        public Builder phoneNumber(String phoneNumber) {
            user.setPhoneNumber(phoneNumber);
            return this;
        }
        public Builder email(String email) {
            user.setEmail(email);
            return this;
        }
        public Builder userName(String userName) {
            user.setUserName(userName);
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
