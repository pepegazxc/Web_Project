package org.example.web_project.Controller;

import org.example.web_project.Service.AccessTokenService;
import org.example.web_project.Service.TextService;
import org.example.web_project.Service.UsersService;
import org.example.web_project.UserController.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@Controller
public class NotesController {

    private final TextService textService;
    private final UsersService usersService;
    private final AccessTokenService accessTokenService;

    @Autowired
    public NotesController(TextService addTextService, UsersService usersService, AccessTokenService accessTokenService) {
        this.textService = addTextService;
        this.usersService = usersService;
        this.accessTokenService = accessTokenService;
    }


    @GetMapping("/index")
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

    @GetMapping("/login")
    public String login() {return "login";}

    @GetMapping("/updateChosenText")
    public String updateChosenText() {return "updateChosenText";}

    @GetMapping("/deleteChosenText")
    public String deleteChosenText() {return "deleteChosenText";}

    @PostMapping("/register")
    public String addNewUser(@ModelAttribute UserRequest userRequest) {
        usersService.addNewUser(userRequest);
        accessTokenService.assignTokenToNewUser();
        System.out.println("Пользователь был успешно добавлен");
        return "index";
    }

    @PostMapping("/text")
    public String addText(@ModelAttribute UserRequest userRequest, Model model) {
        textService.addNewText(userRequest);
        model.addAttribute("messageToUser", "Text has been added!");
        System.out.println("Текст был успешно добавлен");
        return "text";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserRequest userRequest) {
        System.out.println("user_name: " + userRequest.getUser_name());
        System.out.println("password: " + userRequest.getPassword());
        usersService.loginUser(userRequest);
        accessTokenService.assignTokenToLoginUser(userRequest);
        System.out.println("Пользователь успешно вошел!");
        return "index";
    }

    @GetMapping ("/showText")
    public String showText(Model model) {
        Map<Long, String> texts = textService.showAllTexts();
        model.addAttribute("texts", texts);
        return "showText";
    }

    @DeleteMapping("/showText")
    public String deleteAllTexts(Model model) {
        textService.deleteAllTexts();
        model.addAttribute("text", "All texts have been deleted!");
        return "showText";
    }

    @DeleteMapping("/deleteChosenText")
    public String deleteChosenText(@ModelAttribute UserRequest userRequest, Model model) {
        textService.deleteChosenText(userRequest);
        model.addAttribute("messageToUser", "Text has been deleted!");
        return "deleteChosenText";
    }

    @PatchMapping("/updateChosenText")
    public String updateChosenText(@ModelAttribute UserRequest userRequest, Model model) {
        textService.updateChosenText(userRequest);
        model.addAttribute("messageToUser", "Text has been updated!");
        return "updateChosenText";
    }
}
