package com.rtfm.hammer.model.questions;

import com.rtfm.hammer.model.Step;
import com.rtfm.hammer.model.dialogue.Character;

import java.util.List;

import static com.rtfm.hammer.model.StepType.MULTIPLE_CHOICE_QUESTION;

public class MultipleChoiceQuestion extends Step {

    private List<Choice> choices;

    public MultipleChoiceQuestion(Integer id, Character character, String text, List<Choice> choices) {
        super(id, character, text, MULTIPLE_CHOICE_QUESTION);
        this.choices = choices;
    }
}
