package org.example.web_project.Controller;

import org.example.web_project.Service.TextService;
import org.example.web_project.UserController.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NotesController {

    private final TextService addTextService;

    private final List<String> notes = new ArrayList<>();
    private final List<String> texts = new ArrayList<>();

    @Autowired
    public NotesController(TextService addTextService) {
        this.addTextService = addTextService;
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

    @GetMapping("/register")
    public String register() {return "register";}

    @GetMapping("/login")
    public String login() {return "login";}

    @PostMapping("/text")
    public String addText(@RequestBody UserRequest userRequest) {
        addTextService.addNewText(userRequest);
        System.out.println("Текст был успешно добавлен");
        return "text";
    }
}
