package org.example.web_project.Controller;

import org.example.web_project.DTO.UserRequest;
import org.example.web_project.Service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class TextController {

    private final TextService textService;

    @Autowired
    public TextController(TextService textService) {
        this.textService = textService;
    }

    @GetMapping("/text")
    public String textForm() {
        return "text";
    }

    @GetMapping("/updateChosenText")
    public String updateChosenText() {return "updateChosenText";}

    @GetMapping("/deleteChosenText")
    public String deleteChosenText() {return "deleteChosenText";}

    @GetMapping ("/showText")
    public String showText(Model model) {
        Map<Long, String> texts = textService.showAllTexts();
        model.addAttribute("texts", texts);
        return "showText";
    }

    @PostMapping("/text")
    public String addText(@ModelAttribute UserRequest userRequest, Model model) {
        textService.addNewText(userRequest);
        model.addAttribute("messageToUser", "Text has been added!");
        System.out.println("Текст был успешно добавлен");
        return "text";
    }

    @DeleteMapping("/deleteChosenText")
    public String deleteChosenText(@ModelAttribute UserRequest userRequest, Model model) {
        textService.deleteChosenText(userRequest);
        model.addAttribute("messageToUser", "Text has been deleted!");
        return "deleteChosenText";
    }

    @DeleteMapping("/showText")
    public String deleteAllTexts(Model model) {
        textService.deleteAllTexts();
        model.addAttribute("text", "All texts have been deleted!");
        return "showText";
    }

    @PatchMapping("/updateChosenText")
    public String updateChosenText(@ModelAttribute UserRequest userRequest, Model model) {
        textService.updateChosenText(userRequest);
        model.addAttribute("messageToUser", "Text has been updated!");
        return "updateChosenText";
    }
}
