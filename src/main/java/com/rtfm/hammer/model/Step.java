package com.rtfm.hammer.model;

import com.rtfm.hammer.model.dialogue.Character;
import lombok.Getter;

@Getter
public class Step {

    private Integer id;
    private Integer questionId;
    private Integer prevId;
    private Integer nextId;
    private Character character;
    private String text;

}
