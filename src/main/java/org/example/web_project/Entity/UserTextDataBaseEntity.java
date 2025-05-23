package org.example.web_project.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_text")
public class UserTextDataBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "text_id")
    private TextDataBaseEntity text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersDataBaseEntity user;

    public UsersDataBaseEntity getUsers() {
        return user;
    }

    public void setUsers(UsersDataBaseEntity users) {
        this.user = users;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
