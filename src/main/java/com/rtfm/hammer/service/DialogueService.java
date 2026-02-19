package com.rtfm.hammer.service;

import com.rtfm.hammer.model.dialogue.Dialogue;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class DialogueService {

    public List<Dialogue> loadDialogue(String filename) {
        ObjectMapper mapper = new ObjectMapper();
        final var inputStream = getClass().getResourceAsStream("/static/dialogue/" + filename + ".json");
        return mapper.readValue(inputStream, new TypeReference<>() {});
    }

}
