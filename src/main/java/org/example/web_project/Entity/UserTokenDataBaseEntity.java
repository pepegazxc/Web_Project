package org.example.web_project.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_token")
public class UserTokenDataBaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "token_id")
    private AccessTokenDataBaseEntity token;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UsersDataBaseEntity UsersDataBaseEntity;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
