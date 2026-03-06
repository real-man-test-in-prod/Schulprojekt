package com.rtfm.hammer.controller;

import com.rtfm.hammer.service.DialogueService;
import com.rtfm.hammer.service.QuestionProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class RoomController {
    private DialogueService dialogueService;
    private QuestionProvider questionProvider;

    @GetMapping("/rooms/{roomCode}")
    public String getDialogue(@PathVariable String roomCode, Model model) {
        var roomDialogue = dialogueService.loadRoomDialogue(roomCode);
        model.addAttribute("roomDialogue", roomDialogue);

        // Load questions for day 1
        var day1 = roomDialogue.days().get(0);
        var day1Questions = questionProvider.getQuestionsForRoom(roomCode, day1.dailyTest().questionRefs());
        model.addAttribute("day1Questions", day1Questions);

        return "rooms/room";
    }

}
