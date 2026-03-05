package com.rtfm.hammer.controller;

import com.rtfm.hammer.service.DialogueService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class RoomController {
    private DialogueService dialogueService;

    @GetMapping("/rooms/{id}")
    public String getDialogue(@PathVariable Integer id, Model model) {
        model.addAttribute("step", dialogueService.loadDialogue(id));
        model.addAttribute("nextId", id + 1);
        return "rooms/room";
    }

}
