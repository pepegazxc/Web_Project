package org.example.web_project.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "texts")
public class TextDataBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static class Builder {
        private final TextDataBaseEntity textDBEntity = new TextDataBaseEntity();

        public Builder text(String text) {
            textDBEntity.setText(text);
            return this;
        }
        public Builder id(Long id) {
            textDBEntity.setId(id);
            return this;
        }
        public TextDataBaseEntity build() {
            return textDBEntity;
        }
    }
}
