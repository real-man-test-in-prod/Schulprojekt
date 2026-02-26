package com.rtfm.hammer.service;

import com.rtfm.hammer.model.Step;
import com.rtfm.hammer.model.dialogue.Dialogue;
import com.rtfm.hammer.model.questions.BinaryQuestion;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import static com.rtfm.hammer.model.dialogue.Character.SCHUELER;
import static com.rtfm.hammer.model.dialogue.Character.SNILLOR;

@Service
public class DialogueService {

    public String loadDialogue(Integer id) {
        final var step = getStepById(id);
        final var mapper = new ObjectMapper();
        return mapper.writeValueAsString(step);
    }

    private Step getStepById(Integer id) {
        return switch (id) {
            case 0 -> new Dialogue(0, SNILLOR, "Willkommen zu dem Unterricht der Informationslehrenden Krassheit");
            case 1 -> new Dialogue(1, SNILLOR, "Lass uns mit dem Unterricht beginnen!");
            case 2 -> new Dialogue(2, SCHUELER, "Geil!");
            case 3 -> new BinaryQuestion(3, SNILLOR, "Ist Informatik coolio????", true);
            default -> new Dialogue(null, null, "Das solltest du nicht sehen");
        };

    }
}
