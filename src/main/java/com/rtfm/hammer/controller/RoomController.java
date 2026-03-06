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

    @GetMapping("/rooms/{roomCode}")
    public String getDialogue(@PathVariable String roomCode, Model model) {
        model.addAttribute("roomDialogue", dialogueService.loadRoomDialogue(roomCode));
        return "rooms/room";
    }

}
