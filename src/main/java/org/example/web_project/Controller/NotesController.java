package org.example.web_project.Controller;

import org.example.web_project.Service.AccessTokenService;
import org.example.web_project.Service.TextService;
import org.example.web_project.Service.UsersService;
import org.example.web_project.UserController.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NotesController {

    private final TextService textService;
    private final UsersService usersService;
    private final AccessTokenService accessTokenService;

    private final List<String> notes = new ArrayList<>();
    private final List<String> texts = new ArrayList<>();

    @Autowired
    public NotesController(TextService addTextService, UsersService usersService, AccessTokenService accessTokenService) {
        this.textService = addTextService;
        this.usersService = usersService;
        this.accessTokenService = accessTokenService;
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

    @GetMapping("/register")
    public String register() {return "register";}

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserRequest userRequest) {
        usersService.addNewUser(userRequest);
        accessTokenService.userADDToken();
        System.out.println("Пользователь был успешно добавлен");
        return "register";
    }

    @PostMapping("/text")
    public String addText(@RequestBody UserRequest userRequest) {
        textService.addNewText(userRequest);
        System.out.println("Текст был успешно добавлен");
        return "text";
    }

    @GetMapping("/login")
    public String loginUser(@RequestBody UserRequest userRequest) {
        usersService.loginUser(userRequest);
        accessTokenService.assignTokenToUser(userRequest);
        System.out.println("Пользователь успешно вошел!");
        return "index";
    }

    @GetMapping ("/showTexts")
    public String showText() {
        textService.showAllTexts();
        return "showText";
    }

    @DeleteMapping("/showTexts")
    public String deleteAllTexts() {
        textService.deleteAllTexts();
        return "showText";
    }

    @DeleteMapping("/deleteChosenText")
    public String deleteChosenText(@RequestBody UserRequest userRequest) {
        textService.deleteChoosenText(userRequest);
        return "deleteChosenText";
    }
}
