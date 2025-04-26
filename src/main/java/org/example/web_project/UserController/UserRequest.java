package org.example.web_project.UserController;

public class UserRequest {
    private String note;
    private String text;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String Note) {
        this.note = Note;
    }

    public String getText() {
        return text;
    }

    public void setText(String Text) {
        this.text = Text;
    }
}
