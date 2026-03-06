package com.rtfm.hammer.model.dialogue;

import java.util.List;

public record QuestionItem(
        String id,
        String topic,
        String prompt,
        String explanation,
        int points,
        List<AnswerOption> options
) {}
