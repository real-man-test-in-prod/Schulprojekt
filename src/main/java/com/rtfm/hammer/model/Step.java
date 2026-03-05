package com.rtfm.hammer.model;

import com.rtfm.hammer.model.dialogue.Character;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Step {

    private Integer id;
    private Character character;
    private String text;
    private StepType stepType;

}
