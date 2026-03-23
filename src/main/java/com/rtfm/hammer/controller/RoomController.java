package com.rtfm.hammer.controller;

import com.rtfm.hammer.model.dialogue.QuestionItem;
import com.rtfm.hammer.service.DialogueService;
import com.rtfm.hammer.service.QuestionProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class RoomController {
    private DialogueService dialogueService;
    private QuestionProvider questionProvider;

    @GetMapping("/rooms/{roomCode}")
    public String getDialogue(@PathVariable String roomCode, Model model) {
        final var objectMapper = new ObjectMapper();
        var roomDialogue = dialogueService.loadRoomDialogue(roomCode);
        model.addAttribute("roomDialogue", objectMapper.writeValueAsString(roomDialogue));

        List<List<QuestionItem>> allDaysQuestions = new ArrayList<>();
        for (var day : roomDialogue.days()) {
            allDaysQuestions.add(questionProvider.getQuestionsForRoom(roomCode, day.dailyTest().questionRefs()));
        }
        model.addAttribute("allDaysQuestions", objectMapper.writeValueAsString(allDaysQuestions));

        return "rooms/room";
    }

}
