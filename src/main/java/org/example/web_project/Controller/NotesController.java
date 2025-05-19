package org.example.web_project.Controller;

import org.apache.catalina.User;
import org.example.web_project.DTO.NoteDTO;
import org.example.web_project.Service.AccessTokenService;
import org.example.web_project.Service.NoteService;
import org.example.web_project.Service.TextService;
import org.example.web_project.Service.UsersService;
import org.example.web_project.DTO.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class NotesController {

    private final NoteService noteService;

    @Autowired
    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/addNewNote")
    public String noteForm() {
        return "note";
    }

    @GetMapping("/updateChosenNote")
    public String updateChosenNote() {return "updateChosenNote";}

    @GetMapping("/deleteChosenNote")
    public String deleteChosenNote() {return "deleteChosenNote";}

    @PatchMapping("/updateChosenNote")
    public String updateChosenNote(@ModelAttribute UserRequest userRequest, Model model) {
        noteService.updateChosenNote(userRequest);
        model.addAttribute("messageToUser", "Chosen note has been updated!");
        return "updateChosenNote";
    }

    @PostMapping("/addNewNote")
    public String addNewNote(@ModelAttribute UserRequest userRequest, Model model) {
        noteService.addNewNote(userRequest);
        model.addAttribute("messageToUser", "Note has been added!");
        return "note";
    }

    @GetMapping("/showNote")
    public String showNote(Model model) {
        List<NoteDTO> userNotes = noteService.showAllNotesWithData();
        model.addAttribute("userNotes", userNotes);
        return "showNote";
    }

    @DeleteMapping("/deleteChosenNote")
    public String deleteChosenNote(UserRequest userRequest, Model model) {
        noteService.deleteChosenNote(userRequest);
        model.addAttribute("messageToUser", "Chosen note has been deleted!");
        return "deleteChosenNote";
    }

    @DeleteMapping("/showNote")
    public String deleteNote(Model model) {
        noteService.deleteAllUserNotes();
        model.addAttribute("messageToUser", "Note has been deleted!");
        return "showNote";
    }

}
