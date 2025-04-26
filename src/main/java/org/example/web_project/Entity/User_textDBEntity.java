package org.example.web_project.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_text")
public class User_textDBEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "text_id")
    private TextDBEntity text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersDBEntity user;


    public UsersDBEntity getUsers() {
        return user;
    }

    public void setUsers(UsersDBEntity users) {
        this.user = users;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
