package com.rtfm.hammer.model.dialogue;

import com.rtfm.hammer.model.Step;

import static com.rtfm.hammer.model.StepType.DIALOGUE;

public class Dialogue extends Step {

    public Dialogue(Integer id, Character character, String text) {
        super(id, character, text, DIALOGUE);
    }
}
