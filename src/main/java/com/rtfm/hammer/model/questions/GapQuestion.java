package com.rtfm.hammer.model.questions;

import com.rtfm.hammer.model.Step;
import com.rtfm.hammer.model.dialogue.Character;

import java.util.List;

import static com.rtfm.hammer.model.StepType.GAP_QUESTION;

public class GapQuestion extends Step {

    private List<String> correctAnswers;

    public GapQuestion(Integer id, Character character, String text, List<String> correctAnswers) {
        super(id, character, text, GAP_QUESTION);
        this.correctAnswers = correctAnswers;
    }
}
