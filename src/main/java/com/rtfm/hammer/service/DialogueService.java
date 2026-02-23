package com.rtfm.hammer.service;

import com.rtfm.hammer.model.Step;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class DialogueService {

    public String loadDialogue(Integer id) {
        final var step = getStepById(id);
        final var mapper = new ObjectMapper();
        return mapper.writeValueAsString(step);
    }

    private Step getStepById(Integer id) {
        return null;
    }
}
