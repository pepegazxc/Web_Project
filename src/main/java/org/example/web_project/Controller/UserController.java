package org.example.web_project.Controller;

import org.example.web_project.DTO.UserRequest;
import org.example.web_project.Service.AccessTokenService;
import org.example.web_project.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UsersService usersService;
    private final AccessTokenService accessTokenService;

    @Autowired
    public UserController(UsersService usersService, AccessTokenService accessTokenService) {
        this.usersService = usersService;
        this.accessTokenService = accessTokenService;
    }

    @GetMapping("/register")
    public String register() {return "register";}

    @GetMapping("/login")
    public String login() {return "login";}

    @PostMapping("/register")
    public String addNewUser(@ModelAttribute UserRequest userRequest) {
        usersService.addNewUser(userRequest);
        accessTokenService.assignTokenToNewUser();
        System.out.println("Пользователь был успешно добавлен");
        return "index";
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
}
