package com.rtfm.hammer.model.dialogue;

import java.util.List;

public record QuestionItem(
        String id,
        String type,
        String topic,
        String prompt,
        String explanation,
        int points,
        List<Object> options
) {}
