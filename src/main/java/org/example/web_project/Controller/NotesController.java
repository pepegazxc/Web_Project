package org.example.web_project;

import org.example.web_project.Repository.RepositoryForDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NotesController {

    private final RepositoryForDB repositoryForDB;

    private final List<String> notes = new ArrayList<>();
    private final List<String> texts = new ArrayList<>();

    @Autowired
    public NotesController(RepositoryForDB repositoryForDB) {
        this.repositoryForDB = repositoryForDB;
    }

    @GetMapping("/start")
    public String index() {
        return "index";
    }

    @GetMapping("/note")
    public String noteForm() {
        return "note";
    }

    @GetMapping("/text")
    public String textForm() {
        return "text";
    }

    @GetMapping("/showNote")
    public String showNote() {
        return "showNote";
    }

    @GetMapping("/showText")
    public String showText() {
        return "showText";
    }

    @PostMapping("/text")
    public void addText(@RequestBody UserRequest userRequest) {
        repositoryForDB.addTextToDB(userRequest);
    }
}
