package org.example.web_project.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "texts")
public class TextDBEntity {
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
        private final TextDBEntity textDBEntity = new TextDBEntity();

        public Builder text(String text) {
            textDBEntity.setText(text);
            return this;
        }
        public Builder id(Long id) {
            textDBEntity.setId(id);
            return this;
        }
        public TextDBEntity build() {
            return textDBEntity;
        }
    }
}
