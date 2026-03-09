package com.rtfm.hammer.service;

import com.rtfm.hammer.model.Step;
import com.rtfm.hammer.model.dialogue.Dialogue;
import com.rtfm.hammer.model.dialogue.RoomDialogue;
import com.rtfm.hammer.model.questions.BinaryQuestion;
import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import static com.rtfm.hammer.model.dialogue.Character.*;

@Service
public class DialogueService {

    public RoomDialogue loadRoomDialogue(String roomCode) {
        try {
            var resource = new ClassPathResource("static/dialogue/" + roomCode + ".json");
            var mapper = new ObjectMapper();
            return mapper.readValue(resource.getInputStream(), RoomDialogue.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not load dialogue for room: " + roomCode, e);
        }
    }

    public String loadDialogue(Integer id) {
        final var step = getStepById(id);
        final var mapper = new ObjectMapper();
        return mapper.writeValueAsString(step);
    }

    private Step getStepById(Integer id) {
        return switch (id) {
            case 0 -> new Dialogue(0, TEACHER, "Willkommen zu dem Unterricht der Informationslehrenden Krassheit");
            case 1 -> new Dialogue(1, TEACHER, "Lass uns mit dem Unterricht beginnen!");
            case 2 -> new Dialogue(2, SCHUELER, "Geil!");
            case 3 -> new BinaryQuestion(3, TEACHER, "Ist Informatik coolio????", true);
            default -> new Dialogue(null, null, "Das solltest du nicht sehen");
        };

    }
}
