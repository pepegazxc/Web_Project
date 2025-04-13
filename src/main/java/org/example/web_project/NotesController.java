package org.example.web_project;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NotesController {

    private final List<String> notes = new ArrayList<>();
    private final List<String> texts = new ArrayList<>();

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
    
}
