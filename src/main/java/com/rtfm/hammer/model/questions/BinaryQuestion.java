package com.rtfm.hammer.model.questions;

import com.rtfm.hammer.model.Step;
import com.rtfm.hammer.model.dialogue.Character;

import static com.rtfm.hammer.model.StepType.BINARY_QUESTION;

public class BinaryQuestion extends Step {

    private Boolean correctAnswer;

    public BinaryQuestion(Integer id, Character character, String text, Boolean correctAnswer) {
        super(id, character, text, BINARY_QUESTION);
        this.correctAnswer = correctAnswer;
    }
}
